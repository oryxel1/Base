package oxy.bascenario.screens.renderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.esotericsoftware.spine.*;
import com.esotericsoftware.spine.utils.TwoColorPolygonBatch;
import net.lenni0451.commons.color.Color;
import net.lenni0451.commons.color.ColorUtils;
import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.implementation.window.WindowInterface;
import oxy.bascenario.api.elements.effect.OverlayEffectType;
import oxy.bascenario.api.elements.Sprite;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.screens.renderer.base.ElementRenderer;
import oxy.bascenario.utils.ThinGLUtils;

// The fact we're combining libgdx-spine way of rendering with ThinGL is a literal dog shit implement xDDDD
// It's fine however, I don't really care that much...
public class SpriteRenderer extends ElementRenderer<Sprite> {
    private OrthographicCamera camera;
    private TwoColorPolygonBatch batch;
    private SkeletonRenderer renderer;

    private TextureAtlas atlas;
    private Skeleton skeleton;
    private AnimationState state;
    private AnimationStateData stateData;
    public void play(String animation, int index, float duration, boolean loop) {
        stateData.setDefaultMix(duration);
        state.setAnimation(index, animation, loop);
    }

    public SpriteRenderer(Sprite element, RenderLayer layer) {
        super(element, layer);
    }

    @Override
    public void resize(int width, int height) {
        if (this.camera != null) {
            this.camera.setToOrtho(false);
            return;
        }

        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false);
        this.batch = new TwoColorPolygonBatch();

        this.renderer = new SkeletonRenderer();

        this.atlas = new TextureAtlas(new FileHandle(element.atlas().path()));
        SkeletonData skeletonData = new SkeletonBinary(this.atlas).readSkeletonData(new FileHandle(element.skeleton().path()));

        this.skeleton = new Skeleton(skeletonData);
        this.state = new AnimationState(this.stateData = new AnimationStateData(skeletonData));
    }

    @Override
    protected void render() {
        ThinGLUtils.end(); // Hacky, but we need to stop thingl rendering then start again later to avoid conflicts...

        this.state.update(Gdx.graphics.getDeltaTime());
        updateSkeleton(this.skeleton);

        this.camera.position.set(0, 0, 0);
        this.camera.update();
        this.batch.getProjectionMatrix().set(camera.combined);

        this.skeleton.setColor(1, 1, 1, 1);
        this.batch.begin();
        this.renderer.draw(this.batch, this.skeleton);
        this.batch.end();

        if (this.color.color().toRGBA() != Color.WHITE.toRGBA()) {
            final int width = ThinGL.windowInterface().getFramebufferWidth();
            final int height = ThinGL.windowInterface().getFramebufferHeight();
            ThinGL.programs().getColorTweak().bindInput();

            this.batch.begin();
            this.renderer.draw(this.batch, this.skeleton);
            this.batch.end();

            ThinGL.programs().getColorTweak().unbindInput();
            ThinGL.programs().getColorTweak().configureParameters(this.color.color());
            ThinGL.programs().getColorTweak().render(-width, -height, width, height);
            ThinGL.programs().getColorTweak().clearInput();
        }

        if (!this.effects.isEmpty()) {
            ThinGL.globalUniforms().getProjectionMatrix().pushMatrix().setOrtho(0F, 1920, 1080, 0F, -1000F, 1000F);
            ThinGL.programs().getColorTweak().bindInput();

            this.batch.begin();
            this.renderer.draw(this.batch, this.skeleton);
            this.batch.end();

            ThinGL.programs().getColorTweak().unbindInput();
            this.effects.forEach(effect -> renderEffect(effect.getType(), effect.getAxis()));
            ThinGL.programs().getColorTweak().clearInput();
            ThinGL.globalUniforms().getProjectionMatrix().popMatrix();
        }

        ThinGLUtils.start(); // Now start rendering ThinGL again!
    }

    // This already got handled in render()
    @Override
    protected void renderEffects() {
    }

    private void updateSkeleton(Skeleton skeleton) {
        final WindowInterface window = ThinGL.windowInterface();
        int width = window.getFramebufferWidth(), height = window.getFramebufferHeight();

        final float posX = ((this.x.getValue() - 960) / 1920) * width, posY = (this.y.getValue() / 1080) * -height;
        skeleton.setPosition(posX, posY);

        this.state.apply(skeleton);

        // Shitty way to scale and stuff? Well it has already been a problem since the legacy engine model of BASE so.....
        float scale = 0.00046666666F * ((width + height) / 2f);
        skeleton.setScale(scale * this.scale.getValue(), scale * this.scale.getValue());
        skeleton.update(Gdx.graphics.getDeltaTime());
        skeleton.updateWorldTransform(Skeleton.Physics.none);
    }

    @Override
    public void dispose() {
        this.atlas.dispose();
    }
}

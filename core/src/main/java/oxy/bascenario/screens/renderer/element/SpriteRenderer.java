package oxy.bascenario.screens.renderer.element;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.esotericsoftware.spine.*;
import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.implementation.window.WindowInterface;
import oxy.bascenario.api.effects.Effect;
import oxy.bascenario.api.render.elements.Sprite;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.screens.renderer.element.base.ElementRenderer;
import oxy.bascenario.utils.FileUtils;
import oxy.bascenario.utils.ThinGLUtils;

import static oxy.bascenario.utils.ThinGLUtils.GLOBAL_RENDER_STACK;

// The fact we're combining libgdx-spine way of rendering with ThinGL is a literal dog shit implement xDDDD
// It's fine however, I don't really care that much...
public class SpriteRenderer extends ElementRenderer<Sprite> {
    private static final float DEGREES_TO_RADIANS = 0.017453292519943295f;

    private OrthographicCamera camera;
    private PolygonSpriteBatch batch;
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
        this.batch = new PolygonSpriteBatch();

        this.renderer = new SkeletonRenderer();

        this.atlas = new TextureAtlas(FileUtils.toHandle(this.element.atlas()));
        SkeletonData skeletonData = new SkeletonBinary(this.atlas).readSkeletonData(FileUtils.toHandle(element.skeleton()));
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

        if (!this.effects.containsKey(Effect.OUTLINE) || this.effects.size() > 1) {
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
        }

        if (!this.effects.isEmpty()) {
            ThinGL.globalUniforms().getProjectionMatrix().pushMatrix().setOrtho(0F, 1920, 1080, 0F, -1000F, 1000F);

            ThinGLUtils.renderEffect(() -> {
                this.batch.begin();
                this.renderer.draw(this.batch, this.skeleton);
                this.batch.end();
            }, this.effects);

            ThinGL.globalUniforms().getProjectionMatrix().popMatrix();
        }

        ThinGLUtils.start(); // Now start rendering ThinGL again!

        // Should allow for proper offsetting and stuff, won't work with sprite inside sprite, but really if they do that, they're fucking crazy.
        GLOBAL_RENDER_STACK.pushMatrix();
        GLOBAL_RENDER_STACK.rotateXYZ(this.rotation.x() * DEGREES_TO_RADIANS, this.rotation.y() * DEGREES_TO_RADIANS, this.rotation.z() * DEGREES_TO_RADIANS);
        GLOBAL_RENDER_STACK.translate(this.offset.x(), this.offset.y(), 0);
        GLOBAL_RENDER_STACK.translate(this.position.x(), this.position.y(), 0);
        GLOBAL_RENDER_STACK.scale(this.scale.x(), this.scale.y(), 1);
        this.subElements.values().forEach(ElementRenderer::renderAll);
        GLOBAL_RENDER_STACK.popMatrix();
    }

    private void updateSkeleton(Skeleton skeleton) {
        final WindowInterface window = ThinGL.windowInterface();
        int width = window.getFramebufferWidth(), height = window.getFramebufferHeight();

        float x = this.position.x() + this.offset.x(), y = this.position.y() + this.offset.y();
        
        final float posX = ((x - 960) / 1920) * width, posY = (y / 1080) * -height;
        skeleton.setPosition(posX, posY);

        this.state.apply(skeleton);

        // Shitty way to scale and stuff? Well it has already been a problem since the legacy engine model of BASE so.....
        float scale = 0.00046666666F * ((width + height) / 2f);
        skeleton.setScale(scale * this.scale.x(), scale * this.scale.y());
        skeleton.update(Gdx.graphics.getDeltaTime());
        skeleton.updateWorldTransform(Skeleton.Physics.none);
    }

    @Override
    public void dispose() {
        this.atlas.dispose();
    }
}

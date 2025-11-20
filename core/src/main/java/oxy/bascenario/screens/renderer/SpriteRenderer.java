package oxy.bascenario.screens.renderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.esotericsoftware.spine.*;
import com.esotericsoftware.spine.utils.TwoColorPolygonBatch;
import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.implementation.window.WindowInterface;
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
//
        this.atlas = new TextureAtlas(new FileHandle(element.atlas().path()));
        SkeletonData skeletonData = new SkeletonBinary(this.atlas).readSkeletonData(new FileHandle(element.skeleton().path()));

        this.skeleton = new Skeleton(skeletonData);
        this.state = new AnimationState(new AnimationStateData(skeletonData));
    }

    @Override
    public void render() {
        ThinGLUtils.end(); // Hacky, but we need to stop thingl rendering then start again later to avoid conflicts...

        final WindowInterface window = ThinGL.windowInterface();
        int width = window.getFramebufferWidth(), height = window.getFramebufferHeight();

        final float posX = (this.x.getValue() / 1920) * width, posY = (this.y.getValue() / 1080) * -height;
        this.skeleton.setPosition(posX, posY);

        this.state.update(Gdx.graphics.getDeltaTime());
        this.state.apply(this.skeleton);

        // Shitty way to scale and stuff? Well it has already been a problem since the legacy engine model of BASE so.....
        float scale = 0.00046666666F * ((width + height) / 2f);
        this.skeleton.setScale(scale, scale);
        this.skeleton.update(Gdx.graphics.getDeltaTime());
        this.skeleton.updateWorldTransform(Skeleton.Physics.none);

        this.camera.position.set(0, 0, 0);
        this.camera.update();
        this.batch.getProjectionMatrix().set(camera.combined);

        this.batch.begin();
        this.renderer.draw(this.batch, this.skeleton);
        this.batch.end();

        ThinGLUtils.start(); // Now start rendering ThinGL again!
    }

    @Override
    public void dispose() {
        this.atlas.dispose();
    }
}

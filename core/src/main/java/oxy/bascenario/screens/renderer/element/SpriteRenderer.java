package oxy.bascenario.screens.renderer.element;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.esotericsoftware.spine.*;
import imgui.ImGui;
import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.implementation.window.WindowInterface;
import oxy.bascenario.Base;
import oxy.bascenario.api.Scenario;
import oxy.bascenario.api.effects.Effect;
import oxy.bascenario.api.managers.other.AssetType;
import oxy.bascenario.api.render.elements.Sprite;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.api.utils.FileInfo;
import oxy.bascenario.screens.ScenarioScreen;
import oxy.bascenario.screens.renderer.element.base.ElementRenderer;
import oxy.bascenario.utils.files.FileUtils;
import oxy.bascenario.utils.thingl.ThinGLUtils;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static oxy.bascenario.utils.thingl.ThinGLUtils.GLOBAL_RENDER_STACK;

// The fact we're combining libgdx-spine way of rendering with ThinGL is a literal dog shit implement xDDDD
// It's fine however, I don't really care that much...
public class SpriteRenderer extends ElementRenderer<Sprite> {
    private static final float DEGREES_TO_RADIANS = 0.017453292519943295f;

    private OrthographicCamera camera;
    private PolygonSpriteBatch batch;
    private SkeletonRenderer renderer;

//    private TextureAtlas atlas;
    private Skeleton skeleton;
    private AnimationState state;
    private AnimationStateData stateData;

    final List<QueueAnimation> queueAnimations = new CopyOnWriteArrayList<>();
    private record QueueAnimation(String animation, int index, float duration, boolean loop) {
    }
    public void play(String animation, int index, float duration, boolean loop) {
        this.queueAnimations.add(new QueueAnimation(animation, index, duration, loop));
    }

    private final Scenario scenario;
    public SpriteRenderer(Sprite element, RenderLayer layer, Scenario scenario) {
        super(element, layer);
        this.scenario = scenario;
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

        if (element.atlas() == null || element.skeleton() == null) {
            return;
        }

        final FileInfo combined = FileUtils.combine(element.atlas(), element.skeleton());
        SkeletonData skeletonData = Base.instance().assetsManager().get(scenario.getName(), combined, AssetType.SKELETON);

        this.skeleton = new Skeleton(skeletonData);
        this.stateData = new AnimationStateData(skeletonData);
        this.state = new AnimationState(this.stateData);
    }

    @Override
    protected void render() {
        if (this.skeleton == null || this.state == null) {
            return;
        }

        try {
            this.queueAnimations.forEach(animation -> {
                stateData.setDefaultMix(animation.duration);
                state.setAnimation(animation.index, animation.animation, animation.loop);
            });
            this.queueAnimations.clear();
        } catch (Exception ignored) {
        }

        ThinGLUtils.end(); // Hacky, but we need to stop thingl rendering then start again later to avoid conflicts...

        this.state.update(Gdx.graphics.getDeltaTime());
        updateSkeleton(this.skeleton);

        this.camera.position.set(Gdx.graphics.getWidth() / 2f, 0, 0);

        this.camera.up.set(0, 1, 0);
        this.camera.direction.set(0, 0, -1);
        this.camera.rotate(-this.rotation.z());

        this.camera.update();
        this.batch.getProjectionMatrix().set(camera.combined);

        if (!this.effects.containsKey(Effect.OUTLINE) || this.effects.size() > 1) {
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

            if (this.overlayColor.alpha() != 0 && this.color.alpha() != 0) {
                ThinGL.programs().getColorTweak().bindInput();

                this.batch.begin();
                this.renderer.draw(this.batch, this.skeleton);
                this.batch.end();

                ThinGL.programs().getColorTweak().unbindInput();
                ThinGL.programs().getColorTweak().configureParameters(this.overlayColor.color());
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
        GLOBAL_RENDER_STACK.translate(this.pivot.x(), this.pivot.y(), 0);
        GLOBAL_RENDER_STACK.translate(this.offset.x(), this.offset.y(), 0);
        GLOBAL_RENDER_STACK.rotateXYZ(this.rotation.x() * DEGREES_TO_RADIANS, this.rotation.y() * DEGREES_TO_RADIANS, this.rotation.z() * DEGREES_TO_RADIANS);
        GLOBAL_RENDER_STACK.translate(-this.pivot.x(), -this.pivot.y(), 0);
        GLOBAL_RENDER_STACK.translate(-this.offset.x(), -this.offset.y(), 0);
        GLOBAL_RENDER_STACK.translate(this.offset.x(), this.offset.y(), 0);
        GLOBAL_RENDER_STACK.translate(this.position.x(), this.position.y(), 0);
        GLOBAL_RENDER_STACK.scale(this.scale.x(), this.scale.y(), 1);
        this.subElements.values().forEach(ElementRenderer::renderAll);
        GLOBAL_RENDER_STACK.popMatrix();
    }

    private void updateSkeleton(Skeleton skeleton) {
        final WindowInterface window = ThinGL.windowInterface();
        float width = ScenarioScreen.RENDER_WITHIN_IMGUI ? ImGui.getWindowSizeX() : window.getFramebufferWidth(), height = ScenarioScreen.RENDER_WITHIN_IMGUI ? (ImGui.getWindowSizeY() - 23) : window.getFramebufferHeight();

        float x = this.position.x() + this.offset.x(), y = this.position.y() + this.offset.y();
        
        float posX = (x / 1920) * width, posY = (y / 1080) * -height;
        if (ScenarioScreen.RENDER_WITHIN_IMGUI) {
            posX += ImGui.getWindowPosX();

            float ratio = (0.00064814813f * height) / (0.00064814813f * window.getFramebufferHeight());
            posY /= ratio;

            posY += window.getFramebufferHeight() - height;
            posY -= ImGui.getWindowPosY() + 23;
        }

        skeleton.setPosition(posX, posY);

        this.state.apply(skeleton);

        // Shitty way to scale and stuff? Well it has already been a problem since the legacy engine model of BASE so.....
        skeleton.setScale(0.00036458332f * width * this.scale.x(), 0.00064814813f * height * this.scale.y());
        skeleton.update(Gdx.graphics.getDeltaTime());
        skeleton.updateWorldTransform(Skeleton.Physics.none);
    }

//    @Override
//    public void dispose() {
//        this.atlas.dispose();
//    }
}

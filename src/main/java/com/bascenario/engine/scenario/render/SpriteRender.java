package com.bascenario.engine.scenario.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.bascenario.engine.scenario.elements.Sprite;
import com.esotericsoftware.spine.*;
import com.esotericsoftware.spine.utils.TwoColorPolygonBatch;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.lenni0451.commons.animation.DynamicAnimation;
import net.lenni0451.commons.animation.easing.EasingFunction;
import net.lenni0451.commons.animation.easing.EasingMode;
import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.implementation.window.WindowInterface;

@RequiredArgsConstructor
public class SpriteRender {
    @Getter
    private final Sprite sprite;
    private boolean init;

    private OrthographicCamera camera;
    private TwoColorPolygonBatch batch, fadeBatch;

    private SkeletonRenderer renderer;

    private TextureAtlas atlas;
    private Skeleton skeletonFade, skeleton;
    private AnimationState state;

    private DynamicAnimation xLocation = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_IN_OUT, 0, 0), yLocation = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_IN_OUT, 0, 0);
    private DynamicAnimation fadeColor;

    public void init() {
        if (this.init) {
            this.camera.setToOrtho(false);
            return;
        }

        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false);
        this.batch = new TwoColorPolygonBatch();
        this.fadeBatch = new TwoColorPolygonBatch();
        this.renderer = new SkeletonRenderer();

        this.atlas = new TextureAtlas(new FileHandle(this.sprite.atlas()));
        SkeletonBinary json = new SkeletonBinary(this.atlas);
        SkeletonData skeletonData = json.readSkeletonData(new FileHandle(this.sprite.skeleton()));

        this.skeletonFade = new Skeleton(skeletonData);
        this.skeleton = new Skeleton(skeletonData);

        // Initial position.
        this.skeleton.setPosition(0, 0);
        this.skeletonFade.setPosition(0, 0);
        this.state = new AnimationState(new AnimationStateData(skeletonData));

        // This normally should be the idle animation
        this.state.addAnimation(0, this.sprite.defaultAnimation(), true, 0);
        this.init = true;
    }

    public void render() {
        if (!this.init) {
            return;
        }

        if (this.fadeColor == null) {
            this.fadeColor = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_IN_OUT, 300L, 1);

            if (this.sprite.fadeIn()) {
                this.fadeColor.setTarget(0);
            }
        }

        final WindowInterface window = ThinGL.windowInterface();

        // The position is relative to the screen width/height, also the y position should be flip.
        final float posX = (this.xLocation.getValue() / 100) * window.getFramebufferWidth(), posY = (this.yLocation.getValue() / 100) * -window.getFramebufferHeight();
        this.skeleton.setPosition(posX, posY);
        this.skeletonFade.setPosition(posX, posY);

        // Setup stuff before rendering.
        final float delta = Gdx.graphics.getDeltaTime();
        this.state.update(delta);
        this.state.apply(this.skeleton);
        this.state.apply(this.skeletonFade);

        float scale = 0.00046666666F * ((window.getFramebufferWidth() + window.getFramebufferHeight()) / 2f);
        this.skeleton.setScale(scale, scale);
        this.skeleton.update(delta);
        this.skeleton.updateWorldTransform();

        this.skeletonFade.setScale(scale, scale);
        this.skeletonFade.update(delta);
        this.skeletonFade.updateWorldTransform();

        this.camera.position.set(0, 0, 0);
        this.camera.update();
        this.batch.getProjectionMatrix().set(camera.combined);

        this.batch.begin();
        this.skeleton.setColor(new Color(1, 1, 1, 1));
        this.renderer.draw(this.batch, this.skeleton);
        this.batch.end();

        this.fadeBatch.getProjectionMatrix().set(camera.combined);

        // Now we can render!
        if (this.fadeColor.isRunning()) {
            this.fadeBatch.begin();
            this.skeletonFade.setColor(new Color(0, 0, 0, this.fadeColor.getValue()));
            this.renderer.draw(this.fadeBatch, this.skeletonFade);
            this.fadeBatch.end();
        }
    }

    public void playAnimation(int layer, String name, boolean loop) {
        this.state.setAnimation(layer, name, loop);
    }

    public void lerpTo(float targetX, float targetY, long duration) {
        if (duration < 1) {
            this.xLocation = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_IN_OUT, duration, targetX);
            this.yLocation = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_IN_OUT, duration, targetY);
            return;
        }

        this.xLocation = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_IN_OUT, duration, this.xLocation.getValue());
        this.yLocation = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_IN_OUT, duration, this.yLocation.getValue());
        this.xLocation.setTarget(targetX);
        this.yLocation.setTarget(targetY);
    }

    public void dispose() {
        if (this.atlas == null) {
            return;
        }
        this.atlas.dispose();
    }
}

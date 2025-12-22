package oxy.bascenario.screens.renderer.element.thingl.emoticon.impl;

import oxy.bascenario.utils.DynamicAnimation;
import net.lenni0451.commons.animation.easing.EasingFunction;
import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;
import oxy.bascenario.managers.TextureManager;
import oxy.bascenario.screens.renderer.element.thingl.emoticon.base.EmoticonRenderer;
import oxy.bascenario.utils.TimeUtils;
import oxy.bascenario.utils.animation.AnimationUtils;

import static oxy.bascenario.utils.ThinGLUtils.GLOBAL_RENDER_STACK;

// Note emoticon zoom in first, then move to the left, wiggle a bit then fade out.
public class EmoticonNoteRenderer extends EmoticonRenderer {
    private DynamicAnimation offset = AnimationUtils.dummy(0), opacity = AnimationUtils.dummy(1), zoom = AnimationUtils.dummy(0);
    private final DynamicAnimation shake = AnimationUtils.build(400, 0, 4, EasingFunction.LINEAR);

    private long since = -1;

    public EmoticonNoteRenderer(long duration) {
        super(duration);
    }

    @Override
    public void init() {
        this.zoom = AnimationUtils.build(600, 0, 1, EasingFunction.CUBIC);
        this.offset = AnimationUtils.build(2000, 0, -100, EasingFunction.LINEAR);
    }

    @Override
    public void render() {
        if (!this.offset.isRunning()) {
            if (this.since == -1) {
                this.since = TimeUtils.currentTimeMillis();
            }
            if (this.since != -2 && TimeUtils.currentTimeMillis() - this.since >= duration) {
                long distance = TimeUtils.currentTimeMillis() - (TimeUtils.currentTimeMillis() - this.since) - this.duration;

                this.since = -2;
                this.opacity = AnimationUtils.build(600, distance, 1, 0, EasingFunction.LINEAR);
            }
        }

        if (!this.shake.isRunning() && this.offset.isRunning()) {
            this.shake.setTarget(this.shake.getTarget() == 4 ? -4 : 4);
        } else if (!this.offset.isRunning() && this.shake.getTarget() != 0) {
            this.shake.setTarget(0);
        }

        GLOBAL_RENDER_STACK.pushMatrix();
        GLOBAL_RENDER_STACK.rotateZ((float) Math.toRadians(this.shake.getValue()));
        GLOBAL_RENDER_STACK.translate(this.offset.getValue(), 0, 0);
        GLOBAL_RENDER_STACK.scale(this.zoom.getValue());
        ThinGL.renderer2D().coloredTexture(GLOBAL_RENDER_STACK,
                TextureManager.getInstance().getTexture("assets/base/uis/emoticons/Emoticon_Note.png"),
                0, 0, 96, 92, Color.WHITE.withAlphaF(this.opacity.getValue()));
        GLOBAL_RENDER_STACK.popMatrix();
    }

    @Override
    public boolean finished() {
        return !this.opacity.isRunning() && this.since == -2;
    }
}

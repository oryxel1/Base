package oxy.bascenario.screens.renderer.element.thingl.emoticon.impl;

import oxy.bascenario.Base;
import oxy.bascenario.utils.DynamicAnimation;
import net.lenni0451.commons.animation.easing.EasingFunction;
import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.gl.resource.image.texture.impl.Texture2D;
import oxy.bascenario.screens.renderer.element.thingl.emoticon.base.EmoticonRenderer;
import oxy.bascenario.utils.TimeUtils;
import oxy.bascenario.utils.animation.AnimationUtils;

import static oxy.bascenario.utils.ThinGLUtils.GLOBAL_RENDER_STACK;

// Noodle code, too lazy to refactor, it works so it works!
public class EmoticonTwinkleRenderer extends EmoticonRenderer {
    private static final float WIDTH = 121 * 0.7f, HEIGHT = 134 * 0.7f;

    private DynamicAnimation twinkle1 = AnimationUtils.dummy(1), twinkle2 = AnimationUtils.dummy(1), twinkle3 = AnimationUtils.dummy(1);
    private long since = -1;

    public EmoticonTwinkleRenderer(long duration) {
        super(duration);
    }

    @Override
    public void init() {
        this.since = TimeUtils.currentTimeMillis();

        this.twinkle1 = AnimationUtils.build(320, 1, 0.9f, EasingFunction.LINEAR);
        this.twinkle2 = AnimationUtils.build(520, 1, 0.9f, EasingFunction.LINEAR);
        this.twinkle3 = AnimationUtils.build(420, 1, 0.9f, EasingFunction.LINEAR);
    }

    @Override
    public void render() {
        final Texture2D twinkle = Base.instance().assetsManager().texture("assets/base/uis/emoticons/Emoticon_Twinkle.png");

        if (!this.twinkle1.isRunning()) {
            this.twinkle1.setTarget(this.twinkle1.getTarget() == 1 ? 0.9F : 1);
        }
        if (!this.twinkle2.isRunning()) {
            this.twinkle2.setTarget(this.twinkle2.getTarget() == 1 ? 0.9F : 1);
        }
        if (!this.twinkle3.isRunning()) {
            this.twinkle3.setTarget(this.twinkle3.getTarget() == 1 ? 0.9F : 1);
        }

        GLOBAL_RENDER_STACK.pushMatrix();
        GLOBAL_RENDER_STACK.scale(0.8F);
        GLOBAL_RENDER_STACK.scale(this.twinkle1.getValue());
        ThinGL.renderer2D().texture(GLOBAL_RENDER_STACK, twinkle, -(WIDTH + 14) * 0.8F * this.twinkle1.getValue(), 0, WIDTH, HEIGHT);
        GLOBAL_RENDER_STACK.scale(1 / 0.8F);
        GLOBAL_RENDER_STACK.scale(1 / this.twinkle1.getValue());
        GLOBAL_RENDER_STACK.scale(this.twinkle2.getValue());
        GLOBAL_RENDER_STACK.scale(0.4F);
        ThinGL.renderer2D().texture(GLOBAL_RENDER_STACK, twinkle, 0, -(HEIGHT + 7) * 0.4F * this.twinkle2.getValue(), WIDTH, HEIGHT);
        GLOBAL_RENDER_STACK.scale(1 / 0.4F);
        GLOBAL_RENDER_STACK.scale(1 / this.twinkle2.getValue());
        GLOBAL_RENDER_STACK.scale(this.twinkle3.getValue());
        GLOBAL_RENDER_STACK.scale(0.7F);
        ThinGL.renderer2D().texture(GLOBAL_RENDER_STACK, twinkle, 0, (HEIGHT + 7) * (0.7F * this.twinkle3.getValue()), WIDTH, HEIGHT);
        GLOBAL_RENDER_STACK.popMatrix();
    }

    @Override
    public boolean finished() {
        return TimeUtils.currentTimeMillis() - this.since > this.duration;
    }
}

package oxy.bascenario.screens.renderer.element.thingl.emoticon.impl;

import oxy.bascenario.Base;
import oxy.bascenario.utils.animation.DynamicAnimation;
import net.lenni0451.commons.animation.easing.EasingFunction;
import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;
import oxy.bascenario.screens.renderer.element.thingl.emoticon.base.EmoticonRenderer;
import oxy.bascenario.utils.TimeUtils;
import oxy.bascenario.utils.animation.AnimationUtils;

import static oxy.bascenario.utils.thingl.ThinGLUtils.GLOBAL_RENDER_STACK;

public class EmoticonChatRenderer extends EmoticonRenderer {
    private DynamicAnimation opacity = AnimationUtils.dummy(0), rotation = AnimationUtils.dummy(0), scale = AnimationUtils.dummy(0.6f);

    public EmoticonChatRenderer(long duration) {
        super(duration);
    }

    private long since = -1;

    @Override
    public void init() {
        this.rotation = AnimationUtils.build(250, 0, 12, EasingFunction.LINEAR);
        this.opacity = AnimationUtils.build(200, 0, 1, EasingFunction.LINEAR);
        this.scale = AnimationUtils.build(200L, 0.6f, 1, EasingFunction.LINEAR);
        this.since = TimeUtils.currentTimeMillis();
    }

    @Override
    public void render() {
        if (!this.rotation.isRunning()) {
            this.rotation.setTarget(this.rotation.getTarget() == 12 ? -12 : 12);
        }

        if (TimeUtils.currentTimeMillis() - this.since >= this.duration && this.opacity.getTarget() == 1) {
            long distance = TimeUtils.currentTimeMillis() - (TimeUtils.currentTimeMillis() - this.since - this.duration);
            this.opacity = AnimationUtils.build(800, distance, 1, 0, EasingFunction.LINEAR);
        }

        final Color color = Color.WHITE.withAlphaF(opacity.getValue());

        GLOBAL_RENDER_STACK.pushMatrix();
        GLOBAL_RENDER_STACK.scale(this.scale.getValue());
        GLOBAL_RENDER_STACK.rotateZ((float) Math.toRadians(this.rotation.getValue()));
        GLOBAL_RENDER_STACK.translate(-102.9f / 2F, -102.9f / 2F, 0);
        ThinGL.renderer2D().coloredTexture(GLOBAL_RENDER_STACK, Base.instance().assetsManager().texture("assets/base/uis/emoticons/Emoticon_Chat.png"), 0, 0, 102.9f, 143.5f, color);
        GLOBAL_RENDER_STACK.popMatrix();
    }

    @Override
    public boolean finished() {
        return TimeUtils.currentTimeMillis() - this.since >= this.duration && !this.opacity.isRunning() && !(this.opacity instanceof AnimationUtils.DummyAnimation);
    }
}

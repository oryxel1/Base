package oxy.bascenario.screens.renderer.element.thingl.emoticon.impl;

import oxy.bascenario.Base;
import oxy.bascenario.utils.animation.DynamicAnimation;
import net.lenni0451.commons.animation.easing.EasingFunction;
import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.gl.resource.image.texture.impl.Texture2D;
import oxy.bascenario.screens.renderer.element.thingl.emoticon.base.EmoticonRenderer;
import oxy.bascenario.utils.TimeUtils;
import oxy.bascenario.utils.animation.AnimationUtils;

import static oxy.bascenario.utils.thingl.ThinGLUtils.GLOBAL_RENDER_STACK;

public class EmoticonQuestionMarkRenderer extends EmoticonRenderer {
    private DynamicAnimation opacity = AnimationUtils.dummy(1), scale = AnimationUtils.dummy(0.6f);
    private long since = -1;

    public EmoticonQuestionMarkRenderer(long duration) {
        super(duration);
    }

    @Override
    public void init() {
        this.since = TimeUtils.currentTimeMillis();
        this.scale = AnimationUtils.build(200L, 0.6f, 1, EasingFunction.LINEAR);
        this.since = TimeUtils.currentTimeMillis();
    }

    @Override
    public void render() {
        if (TimeUtils.currentTimeMillis() - this.since >= this.duration && this.opacity instanceof AnimationUtils.DummyAnimation) {
            long distance = TimeUtils.currentTimeMillis() - (TimeUtils.currentTimeMillis() - this.since - this.duration);

            this.opacity = AnimationUtils.build(800, distance, 1, 0, EasingFunction.LINEAR);
            this.since = -2;
        }

        final Color color = Color.WHITE.withAlphaF(this.opacity.getValue());
        final Texture2D texture = Base.instance().assetsManager().texture("assets/base/uis/emoticons/Emoticon_QuestionMark.png");

        GLOBAL_RENDER_STACK.pushMatrix();
        GLOBAL_RENDER_STACK.scale(this.scale.getValue());
        ThinGL.renderer2D().coloredTexture(GLOBAL_RENDER_STACK, texture, 0, 35, 94.5f, 130.2f, color);
        GLOBAL_RENDER_STACK.popMatrix();
    }

    @Override
    public boolean finished() {
        return this.since == -2 && !this.opacity.isRunning();
    }
}

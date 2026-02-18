package oxy.bascenario.screens.renderer.element.thingl.emoticon.impl;

import net.lenni0451.commons.animation.easing.EasingFunction;
import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.gl.resource.image.texture.impl.Texture2D;
import oxy.bascenario.Base;
import oxy.bascenario.screens.renderer.element.thingl.emoticon.base.EmoticonRenderer;
import oxy.bascenario.utils.TimeUtils;
import oxy.bascenario.utils.animation.AnimationUtils;
import oxy.bascenario.utils.animation.DynamicAnimation;

import static oxy.bascenario.utils.thingl.ThinGLUtils.GLOBAL_RENDER_STACK;

public class EmoticonSighRenderer extends EmoticonRenderer {
    public EmoticonSighRenderer(long duration) {
        super(duration);
    }

    private DynamicAnimation scale = AnimationUtils.dummy(0), offset = AnimationUtils.dummy(0), opacity = AnimationUtils.dummy(1);

    private long since = -1;
    @Override
    public void init() {
        this.scale = AnimationUtils.build(400L, 0, 1, EasingFunction.QUAD);
    }

    @Override
    public void render() {
        if (this.scale.getValue() > 0.8f && this.offset instanceof AnimationUtils.DummyAnimation) {
            long distance = TimeUtils.currentTimeMillis() - this.scale.resolve(0.8f);
            this.offset = AnimationUtils.build(350L, distance, 0, -30, EasingFunction.SINE);
        }
        if (!this.offset.isRunning() && !(this.offset instanceof AnimationUtils.DummyAnimation)) {
            this.since = TimeUtils.currentTimeMillis() - this.offset.resolve(-30);
        }
        if (this.since != -1 && TimeUtils.currentTimeMillis() - this.since > this.duration && this.opacity instanceof AnimationUtils.DummyAnimation) {
            long distance = TimeUtils.currentTimeMillis() - (TimeUtils.currentTimeMillis() - this.since - this.duration);
            this.opacity = AnimationUtils.build(500L, distance, 1, 0, EasingFunction.LINEAR);
        }

        final Texture2D sigh = Base.instance().assetsManager().texture("assets/base/uis/emojies/Emoji_Sigh.png");

        final Color color = Color.WHITE.withAlphaF(this.opacity.getValue());
        ThinGL.renderer2D().coloredTexture(GLOBAL_RENDER_STACK, sigh, offset.getValue(), -offset.getValue(), 141 / 1.5f * this.scale.getValue(), 121 / 1.5f  * this.scale.getValue(), color);
    }

    @Override
    public boolean finished() {
        return !this.opacity.isRunning() && !(this.opacity instanceof AnimationUtils.DummyAnimation);
    }
}

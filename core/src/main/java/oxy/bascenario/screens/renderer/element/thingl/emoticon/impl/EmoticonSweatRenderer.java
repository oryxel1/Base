package oxy.bascenario.screens.renderer.element.thingl.emoticon.impl;

import oxy.bascenario.utils.DynamicAnimation;
import net.lenni0451.commons.animation.easing.EasingFunction;
import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.gl.resource.image.texture.impl.Texture2D;
import oxy.bascenario.managers.TextureManager;
import oxy.bascenario.screens.renderer.element.thingl.emoticon.base.EmoticonRenderer;
import oxy.bascenario.utils.TimeUtils;
import oxy.bascenario.utils.animation.AnimationUtils;

import static oxy.bascenario.utils.ThinGLUtils.GLOBAL_RENDER_STACK;

public class EmoticonSweatRenderer extends EmoticonRenderer {
    private DynamicAnimation opacity = AnimationUtils.dummy(1);
    private long since = -1;

    private DynamicAnimation firstSweat = AnimationUtils.dummy(0);
    private DynamicAnimation secondSweat = AnimationUtils.dummy(0);

    public EmoticonSweatRenderer(long duration) {
        super(duration);
    }

    @Override
    public void init() {
        this.since = TimeUtils.currentTimeMillis();

        this.firstSweat = AnimationUtils.build((long) (600L / 1.3F), 0, 1, EasingFunction.LINEAR);
        this.secondSweat = AnimationUtils.build((long) (600L / 1.3F), 0, 1, EasingFunction.LINEAR);
        this.opacity = AnimationUtils.build(600L / 3, 0, 1, EasingFunction.LINEAR);
    }

    @Override
    public void render() {
        if (TimeUtils.currentTimeMillis() - this.since >= this.duration && this.opacity instanceof AnimationUtils.DummyAnimation) {
            long distance = TimeUtils.currentTimeMillis() - (TimeUtils.currentTimeMillis() - this.since) - this.duration;

            this.opacity = AnimationUtils.build(800, distance, 1, 0, EasingFunction.LINEAR);
            this.since = -2;
        }

        final Color color = Color.WHITE.withAlphaF(this.opacity.getValue());

        final Texture2D emoticonSweat1 = TextureManager.getInstance().getTexture("assets/base/uis/emoticons/Emoticon_Sweat_1.png");
        final Texture2D emoticonSweat2 = TextureManager.getInstance().getTexture("assets/base/uis/emoticons/Emoticon_Sweat_2.png");

        float sweat1Y = 70 * 0.7f * this.firstSweat.getValue();
        float sweat2Y = 120 * 0.7f * this.secondSweat.getValue();

        ThinGL.renderer2D().coloredTexture(GLOBAL_RENDER_STACK, emoticonSweat1, 0, sweat1Y, 74 * 0.7f, 113 * 0.7f, color);
        ThinGL.renderer2D().coloredTexture(GLOBAL_RENDER_STACK, emoticonSweat2, 70 * 0.7f, sweat2Y, 46 * 0.7f, 65 * 0.7f, color);
    }

    @Override
    public boolean finished() {
        return this.since > 0 && !this.opacity.isRunning();
    }
}

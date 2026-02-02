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

public class EmoticonIdeaRenderer extends EmoticonRenderer {
    private static final float BUBBLE_WIDTH = 178.5f, BUBBLE_HEIGHT = 138.6f;

    private DynamicAnimation opacity = AnimationUtils.dummy(1), lineOpacity = AnimationUtils.dummy(0);

    private long since = TimeUtils.currentTimeMillis();
    private int flash;

    public EmoticonIdeaRenderer(long duration) {
        super(duration);
    }

    @Override
    public void render() {
        if (TimeUtils.currentTimeMillis() - this.since >= this.duration && this.opacity instanceof AnimationUtils.DummyAnimation) {
            long distance = TimeUtils.currentTimeMillis() - (TimeUtils.currentTimeMillis() - this.since - this.duration);

            this.opacity = AnimationUtils.build(800, distance, 1, 0, EasingFunction.LINEAR);
            this.lineOpacity = AnimationUtils.build(800, distance, 1, 0, EasingFunction.LINEAR);
            this.since = -2;
        }

        final Texture2D bubble = Base.instance().assetsManager().texture("assets/base/uis/emoticons/Emoticon_Balloon_N.png");
        final Texture2D bulb1 = Base.instance().assetsManager().texture("assets/base/uis/emojies/Emoji_Bulb_1.png");
        final Texture2D bulb2 = Base.instance().assetsManager().texture("assets/base/uis/emojies/Emoji_Bulb_2.png");

        final Color color = Color.WHITE.withAlphaF(this.opacity.getValue());

        ThinGL.renderer2D().coloredTexture(GLOBAL_RENDER_STACK, bubble, 0, 35, BUBBLE_WIDTH, BUBBLE_HEIGHT, color);

        float bulbWidth = 83 / 1.4f, bulbHeight = 116 / 1.4f;
        if (TimeUtils.currentTimeMillis() - this.since > 200L) {
            ThinGL.renderer2D().coloredTexture(GLOBAL_RENDER_STACK, bulb1, BUBBLE_WIDTH / 2 - (bulbWidth / 2),
                    40 + BUBBLE_HEIGHT / 2 - (bulbHeight / 2), bulbWidth, bulbHeight, color);
        }

        // Pretty sure the line also supposed to flash 1 time, but too lazy to impl that.
        float lineWidth = 130 / 1.4f, lineHeight = 112 / 1.4f;
        ThinGL.renderer2D().coloredTexture(GLOBAL_RENDER_STACK, bulb2, BUBBLE_WIDTH / 2 - (lineWidth / 2) + 1,
                 23 + BUBBLE_HEIGHT / 2 - (lineHeight / 2), lineWidth, lineHeight, Color.WHITE.withAlphaF(lineOpacity.getValue()));

        if (TimeUtils.currentTimeMillis() - this.since > 300L && lineOpacity instanceof AnimationUtils.DummyAnimation) {
            long distance = TimeUtils.currentTimeMillis() - (TimeUtils.currentTimeMillis() - this.since - 300L);
            this.lineOpacity = AnimationUtils.build(60, distance, 0, 1, EasingFunction.LINEAR);
        }
    }

    @Override
    public boolean finished() {
        return this.since == -2 && !this.opacity.isRunning();
    }
}

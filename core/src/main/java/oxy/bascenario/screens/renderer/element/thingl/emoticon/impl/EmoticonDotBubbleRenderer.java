package oxy.bascenario.screens.renderer.element.thingl.emoticon.impl;

import oxy.bascenario.Base;
import oxy.bascenario.utils.DynamicAnimation;
import net.lenni0451.commons.animation.easing.EasingFunction;
import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.gl.resource.image.texture.impl.Texture2D;
import oxy.bascenario.screens.renderer.element.thingl.emoticon.base.EmoticonRenderer;
import oxy.bascenario.utils.TimeUtils;
import oxy.bascenario.utils.animation.AnimationUtils;

import static oxy.bascenario.utils.ThinGLUtils.GLOBAL_RENDER_STACK;

public class EmoticonDotBubbleRenderer extends EmoticonRenderer {
    private static final float DOT_SIZE = 27.3f, TOTAL_DOT_WIDTH = 37.8f * 2 - DOT_SIZE;
    private static final float BUBBLE_WIDTH = 178.5f, THINKING_HEIGHT = 155.4f, HESITATED_HEIGHT = 138.6f;

    private DynamicAnimation opacity = AnimationUtils.dummy(1), scale = AnimationUtils.dummy(0.6f);

    private final boolean hesitated;
    public EmoticonDotBubbleRenderer(long duration, boolean hesitated) {
        super(duration);
        this.hesitated = hesitated;
    }

    private long since = -1, sinceDotIncrement;

    @Override
    public void init() {
        this.scale = AnimationUtils.build(200L, 0.6f, 1, EasingFunction.LINEAR);
        this.sinceDotIncrement = TimeUtils.currentTimeMillis();
    }

    private long dotCount;
    @Override
    public void render() {
        dotCount = Math.min((TimeUtils.currentTimeMillis() - this.sinceDotIncrement) / 600L, 3);

        if (this.opacity instanceof AnimationUtils.DummyAnimation && dotCount == 3) {
            if (this.since == -1) {
                this.since = TimeUtils.currentTimeMillis();
            }

            if (TimeUtils.currentTimeMillis() - this.since >= this.duration) {
                long distance = TimeUtils.currentTimeMillis() - (TimeUtils.currentTimeMillis() - this.since) - this.duration;
                this.opacity = AnimationUtils.build(600 / 3, distance, 1, 0 , EasingFunction.LINEAR);
            }
        }

        final Texture2D bubble = Base.instance().assetsManager().texture("assets/base/uis/emoticons/Emoticon_Balloon_" + (hesitated ? "N.png" : "T.png"));
        final Texture2D dot = Base.instance().assetsManager().texture("assets/base/uis/emoticons/Emoticon_Idea.png");

        GLOBAL_RENDER_STACK.pushMatrix();
        GLOBAL_RENDER_STACK.scale(this.scale.getValue());
        final Color color = Color.WHITE.withAlphaF(opacity.getValue());
        ThinGL.renderer2D().coloredTexture(GLOBAL_RENDER_STACK, bubble, 0, 35, BUBBLE_WIDTH, hesitated ? HESITATED_HEIGHT : THINKING_HEIGHT, color);

        float dotX = BUBBLE_WIDTH / 2 - TOTAL_DOT_WIDTH / 2 + (hesitated ? 8.4f : 9.8f);
        for (int i = 0; i <= dotCount; i++) {
            ThinGL.renderer2D().coloredTexture(GLOBAL_RENDER_STACK, dot,
                    dotX + 37.8f * (i == 0 ? -1 : i == 1 ? 0 : 1),
                    (35 + (hesitated ? HESITATED_HEIGHT : THINKING_HEIGHT) / 2 - DOT_SIZE / 2) - (hesitated ? 2.8f : 10.5f),
                    DOT_SIZE, DOT_SIZE, color);
        }
        GLOBAL_RENDER_STACK.popMatrix();
    }

    @Override
    public boolean finished() {
        return this.dotCount == 3 && !this.opacity.isRunning() && !(this.opacity instanceof AnimationUtils.DummyAnimation);
    }
}

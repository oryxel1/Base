package oxy.bascenario.screens.renderer.element.thingl.emoticon.impl;

import net.lenni0451.commons.animation.DynamicAnimation;
import net.lenni0451.commons.animation.easing.EasingFunction;
import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.gl.resource.image.texture.impl.Texture2D;
import oxy.bascenario.managers.TextureManager;
import oxy.bascenario.screens.renderer.element.thingl.emoticon.base.EmoticonRenderer;
import oxy.bascenario.utils.animation.AnimationUtils;

import static oxy.bascenario.utils.ThinGLUtils.GLOBAL_RENDER_STACK;

public class EmoticonAnxietyRenderer extends EmoticonRenderer {
    private static final float BUBBLE_WIDTH = 178.5f, BUBBLE_HEIGHT = 138.6f;

    private DynamicAnimation opacity = AnimationUtils.dummy(1), scale = AnimationUtils.dummy(0.6f);
    private DynamicAnimation anxietyScaleX = AnimationUtils.dummy(1), anxietyScaleY = AnimationUtils.dummy(1);

    private long since = -1, lastAnxietyTime = -1;
    private boolean prevAnxiety;

    public EmoticonAnxietyRenderer(long duration) {
        super(duration);
    }

    @Override
    public void init() {
        this.scale = AnimationUtils.build(200L, 0.6f, 1, EasingFunction.LINEAR);
        this.anxietyScaleX = AnimationUtils.build(300, 1, 1, EasingFunction.SINE);
        this.anxietyScaleY = AnimationUtils.build(300, 1, 1, EasingFunction.SINE);

        this.since = System.currentTimeMillis();
        this.lastAnxietyTime = System.currentTimeMillis();
    }

    @Override
    public void render() {
        if (System.currentTimeMillis() - this.lastAnxietyTime > 200L) {
            if (this.prevAnxiety) {
                this.anxietyScaleX.setTarget(1.4F);
                this.anxietyScaleY.setTarget(1);
            } else {
                this.anxietyScaleX.setTarget(1);
                this.anxietyScaleY.setTarget(1.4F);
            }

            this.prevAnxiety = !this.prevAnxiety;
            this.lastAnxietyTime = System.currentTimeMillis();
        }

        if (this.since == -1) {
            this.since = System.currentTimeMillis();
        }
        if (System.currentTimeMillis() - this.since >= this.duration && this.opacity instanceof AnimationUtils.DummyAnimation) {
            this.opacity = AnimationUtils.build(800, 1, 0, EasingFunction.LINEAR);
            this.since = -2;
        }

        GLOBAL_RENDER_STACK.pushMatrix();
        GLOBAL_RENDER_STACK.scale(this.scale.getValue());

        final Texture2D bubble = TextureManager.getInstance().getTexture("assets/base/uis/emoticons/Emoticon_Balloon_N.png");
        final Texture2D anxiety = TextureManager.getInstance().getTexture("assets/base/uis/emoticons/Emoticon_Anxiety.png");

        final Color color = Color.WHITE.withAlphaF(this.opacity.getValue());

        float anxietyWidth = 127 * this.anxietyScaleX.getValue() * 0.7f;
        float anxietyHeight = 104 * this.anxietyScaleY.getValue() * 0.7f;
        ThinGL.renderer2D().coloredTexture(GLOBAL_RENDER_STACK, bubble, 0, 35, BUBBLE_WIDTH, BUBBLE_HEIGHT, color);
        ThinGL.renderer2D().coloredTexture(GLOBAL_RENDER_STACK, anxiety, BUBBLE_WIDTH / 2 - (anxietyWidth / 2), 35 + BUBBLE_HEIGHT / 2 - (anxietyHeight / 2), anxietyWidth, anxietyHeight, color);
        GLOBAL_RENDER_STACK.popMatrix();
    }

    @Override
    public boolean finished() {
        return this.since == -2 && !this.opacity.isRunning();
    }
}

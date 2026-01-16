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

public class EmoticonShyOrHeartRenderer extends EmoticonRenderer {
    private static final float BUBBLE_WIDTH = 178.5f, BUBBLE_HEIGHT = 138.6f;

    private DynamicAnimation opacity = AnimationUtils.dummy(1), scale = AnimationUtils.dummy(0.6f), rotation = AnimationUtils.dummy(0);
    private final boolean shy;

    private long since = -1;

    public EmoticonShyOrHeartRenderer(long duration, boolean shy) {
        super(duration);
        this.shy = shy;
    }

    @Override
    public void init() {
        this.scale = AnimationUtils.build(200L, 0.6f, 1, EasingFunction.LINEAR);
        this.rotation = AnimationUtils.build(200, 0, 5, EasingFunction.LINEAR);
        this.since = TimeUtils.currentTimeMillis();
    }

    @Override
    public void render() {
        if (TimeUtils.currentTimeMillis() - this.since >= this.duration && this.opacity instanceof AnimationUtils.DummyAnimation) {
            long distance = TimeUtils.currentTimeMillis() - (TimeUtils.currentTimeMillis() - this.since) - this.duration;

            this.opacity = AnimationUtils.build(800, distance, 1, 0, EasingFunction.LINEAR);
            this.since = -2;
        }

        if (!this.rotation.isRunning()) {
            this.rotation.setTarget(this.rotation.getTarget() == 5 ? -5 : 5);
        }

        GLOBAL_RENDER_STACK.pushMatrix();
        GLOBAL_RENDER_STACK.scale(this.scale.getValue());
        final Texture2D bubble = Base.instance().assetsManager().texture("assets/base/uis/emoticons/Emoticon_Balloon_N.png");
        final Texture2D texture = Base.instance().assetsManager().texture("assets/base/uis/emoticons/" + (shy ? "Emoticon_Shy.png" : "Emoticon_Heart.png"));

        final Color color = Color.WHITE.withAlphaF(this.opacity.getValue());

        float shyWidth = shy ? 233 * 0.7f : 116 * 0.7f, shyHeight = shy ? 152 * 0.7f : 107 * 0.7f;
        ThinGL.renderer2D().coloredTexture(GLOBAL_RENDER_STACK, bubble, 0, 35, BUBBLE_WIDTH, BUBBLE_HEIGHT, color);

        GLOBAL_RENDER_STACK.translate(BUBBLE_WIDTH / 2, 35 + BUBBLE_HEIGHT / 2, 0);
        GLOBAL_RENDER_STACK.rotateZ((float) Math.toRadians(this.rotation.getValue()));
        GLOBAL_RENDER_STACK.translate(-shyWidth / 2F, -shyHeight / 2F, 0);
        ThinGL.renderer2D().coloredTexture(GLOBAL_RENDER_STACK, texture, 0, -5, shyWidth, shyHeight, color);
        GLOBAL_RENDER_STACK.popMatrix();
    }

    @Override
    public boolean finished() {
        return this.since == -2 && !this.opacity.isRunning();
    }
}

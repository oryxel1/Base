package oxy.bascenario.screens.renderer.element.thingl.emoticon.impl;

import net.lenni0451.commons.animation.DynamicAnimation;
import net.lenni0451.commons.animation.easing.EasingFunction;
import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.resource.image.texture.Texture2D;
import oxy.bascenario.managers.TextureManager;
import oxy.bascenario.screens.renderer.element.thingl.emoticon.base.EmoticonRenderer;
import oxy.bascenario.utils.animation.AnimationUtils;

import static oxy.bascenario.utils.ThinGLUtils.GLOBAL_RENDER_STACK;

public class EmoticonExclamationMarkRenderer extends EmoticonRenderer {
    private DynamicAnimation opacity = AnimationUtils.dummy(1), scale = AnimationUtils.dummy(0.6f);
    private long since = -1;

    public EmoticonExclamationMarkRenderer(long duration) {
        super(duration);
    }

    @Override
    public void init() {
        this.since = System.currentTimeMillis();
        this.scale = AnimationUtils.build(200L, 0.6f, 1, EasingFunction.LINEAR);
    }

    @Override
    public void render() {
        if (this.since == -1) {
            this.since = System.currentTimeMillis();
        }
        if (System.currentTimeMillis() - this.since >= this.duration && this.opacity instanceof AnimationUtils.DummyAnimation) {
            this.opacity = AnimationUtils.build(800, 1, 0, EasingFunction.LINEAR);
            this.since = -2;
        }

        final Color color = Color.WHITE.withAlphaF(this.opacity.getValue());
        final Texture2D texture = TextureManager.getInstance().getTexture("assets/base/uis/emoticons/Emoticon_ExclamationMark.png");

        GLOBAL_RENDER_STACK.pushMatrix();
        GLOBAL_RENDER_STACK.scale(this.scale.getValue());
        ThinGL.renderer2D().coloredTexture(GLOBAL_RENDER_STACK, texture, 0, 35, 62.3f, 128.1f, color);
        GLOBAL_RENDER_STACK.popMatrix();
    }

    @Override
    public boolean finished() {
        return this.since == -2 && !this.opacity.isRunning();
    }
}

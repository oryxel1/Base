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

// I think there is extra animation to this blah blah blah but too lazy so this will be a TODO for now.
public class EmoticonAngryRenderer extends EmoticonRenderer {
    private DynamicAnimation opacity = AnimationUtils.dummy(1), scale = AnimationUtils.dummy(0.6f);
    private long since = -1;

    public EmoticonAngryRenderer(long duration) {
        super(duration);
    }

    @Override
    public void init() {
        this.since = TimeUtils.currentTimeMillis();
        this.scale = AnimationUtils.build(200L, 0.6f, 1, EasingFunction.LINEAR);
    }

    @Override
    public void render() {
        if (this.since == -1) {
            this.since = TimeUtils.currentTimeMillis();
        }
        if (TimeUtils.currentTimeMillis() - this.since >= this.duration && this.opacity instanceof AnimationUtils.DummyAnimation) {
            this.opacity = AnimationUtils.build(800, 1, 0, EasingFunction.LINEAR);
            this.since = -2;
        }

        final Color color = Color.WHITE.withAlphaF(this.opacity.getValue());
        final Texture2D aggro = TextureManager.getInstance().getTexture("assets/base/uis/emoticons/Emoticon_Aggro.png");

        GLOBAL_RENDER_STACK.pushMatrix();
        GLOBAL_RENDER_STACK.scale(this.scale.getValue());
        ThinGL.renderer2D().coloredTexture(GLOBAL_RENDER_STACK, aggro, 0, 0, 65.8f, 46.9f, color);
        GLOBAL_RENDER_STACK.rotateZ((float) Math.toRadians(90));
        ThinGL.renderer2D().coloredTexture(GLOBAL_RENDER_STACK, aggro, -35, -7, 65.8f, 46.9f, color);

        GLOBAL_RENDER_STACK.translate(-65.8f + 65.8f / 2F, -57.4f + 46.9f / 2F, 0);
        GLOBAL_RENDER_STACK.rotateZ((float) Math.toRadians(-210));
        GLOBAL_RENDER_STACK.translate(-65.8f / 2F, -46.9f / 2F, 0);
        ThinGL.renderer2D().coloredTexture(GLOBAL_RENDER_STACK, aggro, 0, 0, 65.8f, 46.9f, color);
        GLOBAL_RENDER_STACK.popMatrix();
    }

    @Override
    public boolean finished() {
        return this.since == -2 && !this.opacity.isRunning();
    }
}

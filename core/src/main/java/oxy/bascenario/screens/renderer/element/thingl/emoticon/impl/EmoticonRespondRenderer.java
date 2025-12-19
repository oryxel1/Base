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

public class EmoticonRespondRenderer extends EmoticonRenderer {
    private DynamicAnimation opacity = AnimationUtils.dummy(1);
    private long since = -1;

    public EmoticonRespondRenderer(long duration) {
        super(duration);
    }

    @Override
    public void init() {
        this.since = System.currentTimeMillis();
    }

    @Override
    public void render() {
        if (System.currentTimeMillis() - this.since >= this.duration && this.opacity instanceof AnimationUtils.DummyAnimation) {
            this.opacity = AnimationUtils.build(800, 1, 0, EasingFunction.LINEAR);
            this.since = -2;
        }

        final Color color = Color.WHITE.withAlphaF(this.opacity.getValue());

        final Texture2D action = TextureManager.getInstance().getTexture("assets/base/uis/emoticons/Emoticon_Action.png");

        ThinGL.renderer2D().coloredTexture(GLOBAL_RENDER_STACK, action, 0, 0, 90, 36, color);

        GLOBAL_RENDER_STACK.pushMatrix();
        GLOBAL_RENDER_STACK.rotateZ((float) Math.toRadians(-20));
        ThinGL.renderer2D().coloredTexture(GLOBAL_RENDER_STACK, action, 0, 57, 90, 36, color);

        GLOBAL_RENDER_STACK.rotateZ((float) Math.toRadians(40));
        ThinGL.renderer2D().coloredTexture(GLOBAL_RENDER_STACK, action, 0, -57, 90, 36, color);
        GLOBAL_RENDER_STACK.popMatrix();
    }

    @Override
    public boolean finished() {
        return this.since == -2 && !this.opacity.isRunning();
    }
}

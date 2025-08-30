package com.bascenario.engine.scenario.render;

import com.bascenario.engine.scenario.elements.Sprite;
import com.bascenario.render.manager.TextureManager;
import net.lenni0451.commons.animation.DynamicAnimation;
import net.lenni0451.commons.animation.easing.EasingFunction;
import net.lenni0451.commons.animation.easing.EasingMode;
import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.implementation.window.WindowInterface;
import net.raphimc.thingl.resource.image.texture.Texture2D;
import org.joml.Matrix4fStack;

public class EmoticonRender {
    private final Sprite.Emoticon emoticon;

    // Every emoticon should use this... I think.
    private final DynamicAnimation fadeInAnimation;

    // Now individuals for type.
    private final DynamicAnimation sweat1Animation, sweat2Animation;

    public EmoticonRender(Sprite.Emoticon emoticon) {
        this.emoticon = emoticon;

        this.fadeInAnimation = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_IN_OUT, emoticon.duration() / 3, 0);

        this.sweat1Animation = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_IN_OUT, (long) (emoticon.duration() / 1.3F), -20);
        this.sweat2Animation = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_IN_OUT, emoticon.duration(), -50);
        this.sweat1Animation.setTarget(50);
        this.sweat2Animation.setTarget(50);
    }

    public void render(final Matrix4fStack positionMatrix, float x, float y) {
        final WindowInterface window = ThinGL.windowInterface();
        if (this.fadeInAnimation.getTarget() == 0) {
            this.fadeInAnimation.setTarget(255);
        }

        final Color color = Color.fromRGBA(255, 255, 255, !this.fadeInAnimation.isRunning() ? 255 : Math.round(this.fadeInAnimation.getValue()));

        switch (this.emoticon.type()) {
            case SWEAT -> {
                final Texture2D emoticonSweat1 = TextureManager.getInstance().getTexture("/assets/base/uis/emoticons/Emoticon_Sweat_1.png");
                final Texture2D emoticonSweat2 = TextureManager.getInstance().getTexture("/assets/base/uis/emoticons/Emoticon_Sweat_2.png");

                float offsetX = (this.emoticon.offsetX() / 1920) * window.getFramebufferWidth(),
                        offsetY = (this.emoticon.offsetY() / 1080) * window.getFramebufferHeight();

                positionMatrix.pushMatrix();
                positionMatrix.translate(x, y, 0);
                positionMatrix.translate(offsetX, offsetY, 0);
                positionMatrix.scale(0.7F);
                ThinGL.renderer2D().coloredTexture(positionMatrix, emoticonSweat1, 0, this.sweat1Animation.getValue(),
                        0.03854166666F * window.getFramebufferWidth(), 0.10555555555F * window.getFramebufferHeight(),
                        color);
                ThinGL.renderer2D().coloredTexture(positionMatrix, emoticonSweat2, 0.03645833333F * window.getFramebufferWidth(), this.sweat2Animation.getValue(),
                        0.02395833333F * window.getFramebufferWidth(), 0.06018518518F * window.getFramebufferHeight(),
                        color);
                positionMatrix.popMatrix();
            }
        }
    }
}

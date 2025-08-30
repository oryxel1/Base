package com.bascenario.engine.scenario.render;

import com.bascenario.engine.scenario.elements.Sprite;
import com.bascenario.render.manager.TextureManager;
import lombok.Getter;
import net.lenni0451.commons.animation.DynamicAnimation;
import net.lenni0451.commons.animation.easing.EasingFunction;
import net.lenni0451.commons.animation.easing.EasingMode;
import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.implementation.window.WindowInterface;
import net.raphimc.thingl.resource.image.texture.Texture2D;
import org.joml.Matrix4fStack;

public class EmoticonRender {
    @Getter
    private final Sprite.Emoticon emoticon;

    // Every emoticon should use this... I think.
    private final DynamicAnimation fadeInAnimation;
    private final DynamicAnimation globalFadeOut;
    private final DynamicAnimation globalScale;

    // Now individuals for type.
    private final DynamicAnimation sweat1Animation, sweat2Animation;
    private final DynamicAnimation anxietyScaleX, anxietyScaleY;

    private boolean lastAnxiety;
    private long lastAnxietyTime;
    public EmoticonRender(Sprite.Emoticon emoticon) {
        this.emoticon = emoticon;

        this.fadeInAnimation = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_IN_OUT, 600L / 3, 0);

        this.sweat1Animation = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_IN_OUT, (long) (600L / 1.3F), -20);
        this.sweat2Animation = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_IN_OUT, 600L, -80);
        this.sweat1Animation.setTarget(50);
        this.sweat2Animation.setTarget(50);

        float scale = 0.6F;
        if (emoticon.type() == Sprite.EmoticonType.ANXIETY) {
            scale = 0.7F;
        }

        this.globalScale = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_IN, 200L, scale);
        this.globalScale.setTarget(1);

        this.globalFadeOut = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_OUT, 400L, 255);

        this.anxietyScaleX = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_IN_OUT, 300L, 1);
        this.anxietyScaleY = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_IN_OUT, 300L, 1);
    }

    public void render(final Matrix4fStack positionMatrix, float x, float y) {
        final WindowInterface window = ThinGL.windowInterface();
        if (this.fadeInAnimation.getTarget() == 0) {
            this.fadeInAnimation.setTarget(255);
        }

        float scale = 1050 / ((window.getFramebufferWidth() + window.getFramebufferHeight()) / 2f);

        final float offsetX = (this.emoticon.offsetX() / 1920) * window.getFramebufferWidth(), offsetY = (this.emoticon.offsetY() / 1080) * window.getFramebufferHeight();
        positionMatrix.translate(x, y, 0);
        positionMatrix.translate(offsetX, offsetY, 0);
        positionMatrix.scale(scale);

        positionMatrix.pushMatrix();
        switch (this.emoticon.type()) {
            case SWEAT -> {
                final Color color = Color.fromRGBA(255, 255, 255, !this.fadeInAnimation.isRunning() ? 255 : Math.round(this.fadeInAnimation.getValue()));
                final Texture2D emoticonSweat1 = TextureManager.getInstance().getTexture("/assets/base/uis/emoticons/Emoticon_Sweat_1.png");
                final Texture2D emoticonSweat2 = TextureManager.getInstance().getTexture("/assets/base/uis/emoticons/Emoticon_Sweat_2.png");

                ThinGL.renderer2D().coloredTexture(positionMatrix, emoticonSweat1, 0, this.sweat1Animation.getValue(),
                        0.03854166666F * window.getFramebufferWidth(), 0.10555555555F * window.getFramebufferHeight(),
                        color);
                ThinGL.renderer2D().coloredTexture(positionMatrix, emoticonSweat2, 0.03645833333F * window.getFramebufferWidth(), this.sweat2Animation.getValue(),
                        0.02395833333F * window.getFramebufferWidth(), 0.06018518518F * window.getFramebufferHeight(),
                        color);
            }
            case EXCLAMATION_MARK -> {
                final Texture2D texture = TextureManager.getInstance().getTexture("/assets/base/uis/emoticons/Emoticon_ExclamationMark.png");

                final Color color = Color.fromRGBA(255, 255, 255, !this.globalFadeOut.isRunning() ? 255 : Math.round(this.globalFadeOut.getValue()));
                positionMatrix.scale(this.globalScale.getValue());
                ThinGL.renderer2D().coloredTexture(positionMatrix, texture, 0, 50, 0.04635416666F * window.getFramebufferWidth(), 0.16944444444F * window.getFramebufferHeight(), color);

                if (!this.globalScale.isRunning() && System.currentTimeMillis() - time + 500L >= this.emoticon.duration() && this.globalFadeOut.getTarget() == 255) {
                    this.globalFadeOut.setTarget(0);
                }
            }
            case ANXIETY -> {
                if (System.currentTimeMillis() - this.lastAnxietyTime > 200L) {
                    if (this.lastAnxiety) {
                        this.anxietyScaleX.setTarget(1.4F);
                        this.anxietyScaleY.setTarget(1);
                    } else {
                        this.anxietyScaleX.setTarget(1);
                        this.anxietyScaleY.setTarget(1.4F);
                    }

                    this.lastAnxiety = !this.lastAnxiety;
                    this.lastAnxietyTime = System.currentTimeMillis();
                }

                final Texture2D bubble = TextureManager.getInstance().getTexture("/assets/base/uis/emoticons/Emoticon_Balloon_N.png");
                final Texture2D anxiety = TextureManager.getInstance().getTexture("/assets/base/uis/emoticons/Emoticon_Anxiety.png");

                positionMatrix.scale(this.globalScale.getValue());

                final Color color = Color.fromRGBA(255, 255, 255, !this.globalFadeOut.isRunning() ? 255 : Math.round(this.globalFadeOut.getValue()));
                float bubbleWidth = 0.1328125F * window.getFramebufferWidth();
                float bubbleHeight = 0.18333333333F * window.getFramebufferHeight();

                float anxietyWidth = 0.0819128788F * this.anxietyScaleX.getValue() * window.getFramebufferWidth();
                float anxietyHeight = 0.10606060605F * this.anxietyScaleY.getValue() * window.getFramebufferHeight();
                ThinGL.renderer2D().coloredTexture(positionMatrix, bubble, 0, 50, bubbleWidth, bubbleHeight, color);

                ThinGL.renderer2D().coloredTexture(positionMatrix, anxiety, bubbleWidth / 2 - (anxietyWidth / 2), 50 + bubbleHeight / 2 - (anxietyHeight / 2), anxietyWidth, anxietyHeight, color);

                if (!this.globalScale.isRunning() && System.currentTimeMillis() - this.time + 500L >= this.emoticon.duration() && this.globalFadeOut.getTarget() == 255) {
                    this.globalFadeOut.setTarget(0);
                }
            }
        }

        positionMatrix.popMatrix();
    }

    private final long time = System.currentTimeMillis();
    public boolean isFinished() {
        if (this.emoticon.type() == Sprite.EmoticonType.EXCLAMATION_MARK) {
            if (this.globalFadeOut.isRunning()) {
                return false;
            }
        }

        return System.currentTimeMillis() - time >= this.emoticon.duration();
    }
}

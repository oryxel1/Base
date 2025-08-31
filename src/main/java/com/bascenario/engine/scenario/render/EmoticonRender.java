package com.bascenario.engine.scenario.render;

import com.bascenario.engine.scenario.elements.Sprite;
import com.bascenario.render.manager.TextureManager;
import com.bascenario.util.math.MathUtil;
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
    private long lastDotTime = System.currentTimeMillis();
    private int dotCount;

    public EmoticonRender(Sprite.Emoticon emoticon) {
        this.emoticon = emoticon;

        this.fadeInAnimation = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_IN_OUT, 600L / 3, 0);

        this.sweat1Animation = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_IN_OUT, (long) (600L / 1.3F), 0);
        this.sweat2Animation = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_IN_OUT, 600L, 0);
        this.sweat1Animation.setTarget(1);
        this.sweat2Animation.setTarget(1);

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

        float averageScale = 0.00046666666F * ((window.getFramebufferWidth() + window.getFramebufferHeight()) / 2f);
//        float averageScale = Math.min(window.getFramebufferWidth() / 1920F, window.getFramebufferHeight() / 1080F);

        positionMatrix.pushMatrix();
        positionMatrix.translate(x, y, 0);
        positionMatrix.translate(this.emoticon.offsetX() * averageScale, this.emoticon.offsetY() * averageScale, 0);
        positionMatrix.scale(averageScale);

        switch (this.emoticon.type()) {
            case SWEAT -> {
                final Color color = Color.fromRGBA(255, 255, 255, !this.fadeInAnimation.isRunning() ? 255 : Math.round(this.fadeInAnimation.getValue()));
                final Texture2D emoticonSweat1 = TextureManager.getInstance().getTexture("/assets/base/uis/emoticons/Emoticon_Sweat_1.png");
                final Texture2D emoticonSweat2 = TextureManager.getInstance().getTexture("/assets/base/uis/emoticons/Emoticon_Sweat_2.png");

                float sweat1Y = 70 * this.sweat1Animation.getValue();
                float sweat2Y = 120 * this.sweat2Animation.getValue();

                ThinGL.renderer2D().coloredTexture(positionMatrix, emoticonSweat1, 0, sweat1Y, 74, 113, color);
                ThinGL.renderer2D().coloredTexture(positionMatrix, emoticonSweat2, 70, sweat2Y, 46, 65, color);
            }
            case EXCLAMATION_MARK -> {
                final Texture2D texture = TextureManager.getInstance().getTexture("/assets/base/uis/emoticons/Emoticon_ExclamationMark.png");

                final Color color = Color.fromRGBA(255, 255, 255, !this.globalFadeOut.isRunning() ? 255 : Math.round(this.globalFadeOut.getValue()));
                positionMatrix.scale(this.globalScale.getValue());
                ThinGL.renderer2D().coloredTexture(positionMatrix, texture, 0, 50, 89, 183, color);

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
                float bubbleWidth = 255, bubbleHeight = 198;

                float anxietyWidth = 157 * this.anxietyScaleX.getValue();
                float anxietyHeight = 114 * this.anxietyScaleY.getValue();
                ThinGL.renderer2D().coloredTexture(positionMatrix, bubble, 0, 50, bubbleWidth, bubbleHeight, color);
                ThinGL.renderer2D().coloredTexture(positionMatrix, anxiety, bubbleWidth / 2 - (anxietyWidth / 2), 50 + bubbleHeight / 2 - (anxietyHeight / 2), anxietyWidth, anxietyHeight, color);

                if (!this.globalScale.isRunning() && System.currentTimeMillis() - this.time + 500L >= this.emoticon.duration() && this.globalFadeOut.getTarget() == 255) {
                    this.globalFadeOut.setTarget(0);
                }
            }
            case THINKING, HESITATED -> {
                if (!this.globalScale.isRunning() && System.currentTimeMillis() - this.time + 500L >= this.emoticon.duration() && this.globalFadeOut.getTarget() == 255 && dotCount == 3) {
                    this.globalFadeOut.setTarget(0);
                }

                positionMatrix.scale(this.globalScale.getValue());

                boolean hesitated = this.emoticon.type() == Sprite.EmoticonType.HESITATED;
                final Texture2D bubble = TextureManager.getInstance().getTexture("/assets/base/uis/emoticons/Emoticon_Balloon_" + (hesitated ? "N.png" : "T.png"));
                final Texture2D dot = TextureManager.getInstance().getTexture("/assets/base/uis/emoticons/Emoticon_Idea.png");

                final Color color = Color.fromRGBA(255, 255, 255, !this.globalFadeOut.isRunning() ? 255 : Math.round(this.globalFadeOut.getValue()));

                float bubbleWidth = 255, bubbleHeight = hesitated ? 198 : 222;
                ThinGL.renderer2D().coloredTexture(positionMatrix, bubble, 0, 50, bubbleWidth, bubbleHeight, color);

                if (System.currentTimeMillis() - this.lastDotTime > 600L && dotCount < 3) {
                    this.lastDotTime = System.currentTimeMillis();
                    dotCount++;
                }

                float dotWidthAndHeight = 39;
                float maxDotWidth = 54 * 2 - dotWidthAndHeight;

                float dotX = bubbleWidth / 2 - maxDotWidth / 2;
                for (int i = 0; i <= dotCount; i++) {
                    ThinGL.renderer2D().coloredTexture(positionMatrix, dot, dotX + 54 * (i == 0 ? -1 : i == 1 ? 0 : 1), 50 + bubbleHeight / 2 - dotWidthAndHeight / 2, dotWidthAndHeight, dotWidthAndHeight, color);
                }
            }
        }

        positionMatrix.popMatrix();
    }

    private final long time = System.currentTimeMillis();
    public boolean isFinished() {
        if (this.emoticon.type() == Sprite.EmoticonType.EXCLAMATION_MARK || this.emoticon.type() == Sprite.EmoticonType.ANXIETY
                || this.emoticon.type() == Sprite.EmoticonType.HESITATED || this.emoticon.type() == Sprite.EmoticonType.THINKING) {
            if (this.globalFadeOut.isRunning()) {
                return false;
            }
        }

        if (this.emoticon.type() == Sprite.EmoticonType.HESITATED || this.emoticon.type() == Sprite.EmoticonType.THINKING) {
            if (dotCount != 3) {
                return false;
            }
        }

        return System.currentTimeMillis() - time >= this.emoticon.duration();
    }
}

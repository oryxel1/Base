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

// Please don't look at this class, it's really a mess.
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
    private final DynamicAnimation shyAnimation, chatAnimation;
    private final DynamicAnimation twinkle1, twinkle2, twinkle3;
    private final DynamicAnimation suprisedEM, suprisedQM;
    private final DynamicAnimation note, noteShake;

    private boolean lastAnxiety;
    private long lastAnxietyTime;
    private long lastDotTime = System.currentTimeMillis();
    private int dotCount, flipCount;

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

        this.shyAnimation = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_IN_OUT, 200L, 0);
        this.shyAnimation.setTarget(5);

        this.chatAnimation = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_IN_OUT, 250L, 0);
        this.chatAnimation.setTarget(12);

        this.twinkle1 = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_IN_OUT, 320L, 1);
        this.twinkle1.setTarget(0.9F);

        this.twinkle2 = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_IN_OUT, 520L, 1);
        this.twinkle2.setTarget(0.9F);

        this.twinkle3 = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_IN_OUT, 420L, 1);
        this.twinkle3.setTarget(0.9F);

        this.suprisedEM = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_IN_OUT, 120L, 0.6F);
        this.suprisedQM = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_IN_OUT, 220L, 0.6F);
        this.suprisedEM.setTarget(1);
        this.suprisedQM.setTarget(1);

        this.note = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_IN_OUT, 520L, -10);
        this.note.setTarget(-50);
        this.noteShake = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_IN_OUT, 120L, 0);
        this.noteShake.setTarget(6);
    }

    public void render(final Matrix4fStack positionMatrix, final SpriteRender render, float x, float y) {
        final WindowInterface window = ThinGL.windowInterface();
        if (this.fadeInAnimation.getTarget() == 0) {
            this.fadeInAnimation.setTarget(255);
        }

        float averageScale = 0.00046666666F * render.getScale().getValue() * ((window.getFramebufferWidth() + window.getFramebufferHeight()) / 2f);
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
            case QUESTION_MARK -> {
                final Texture2D texture = TextureManager.getInstance().getTexture("/assets/base/uis/emoticons/Emoticon_QuestionMark.png");

                final Color color = Color.fromRGBA(255, 255, 255, !this.globalFadeOut.isRunning() ? 255 : Math.round(this.globalFadeOut.getValue()));
                positionMatrix.scale(this.globalScale.getValue());
                ThinGL.renderer2D().coloredTexture(positionMatrix, texture, 0, 50, 135, 186, color);

                if (!this.globalScale.isRunning() && System.currentTimeMillis() - time + 500L >= this.emoticon.duration() && this.globalFadeOut.getTarget() == 255) {
                    this.globalFadeOut.setTarget(0);
                }
            }
            case TWINKLE -> {
                if (!this.globalScale.isRunning() && System.currentTimeMillis() - time + 500L >= this.emoticon.duration() && this.globalFadeOut.getTarget() == 255) {
                    this.globalFadeOut.setTarget(0);
                }

                final Texture2D twinkle = TextureManager.getInstance().getTexture("/assets/base/uis/emoticons/Emoticon_Twinkle.png");

                if (this.twinkle1.getValue() == 1) {
                    this.twinkle1.setTarget(0.9F);
                } else if (this.twinkle1.getValue() == 0.9F) {
                    this.twinkle1.setTarget(1);
                }
                if (this.twinkle2.getValue() == 1) {
                    this.twinkle2.setTarget(0.9F);
                } else if (this.twinkle2.getValue() == 0.9F) {
                    this.twinkle2.setTarget(1);
                }
                if (this.twinkle3.getValue() == 1) {
                    this.twinkle3.setTarget(0.9F);
                } else if (this.twinkle3.getValue() == 0.9F) {
                    this.twinkle3.setTarget(1);
                }

                float twinkleWidth = 121, twinkleHeight = 134;
                positionMatrix.scale(0.8F);
                positionMatrix.scale(this.twinkle1.getValue());
                ThinGL.renderer2D().texture(positionMatrix, twinkle, -(twinkleWidth + 20) * 0.8F * this.twinkle1.getValue(), 0, twinkleWidth, twinkleHeight);
                positionMatrix.scale(1 / 0.8F);
                positionMatrix.scale(1 / this.twinkle1.getValue());
                positionMatrix.scale(this.twinkle2.getValue());
                positionMatrix.scale(0.4F);
                ThinGL.renderer2D().texture(positionMatrix, twinkle, 0, -(twinkleHeight + 10) * 0.4F * this.twinkle2.getValue(), twinkleWidth, twinkleHeight);
                positionMatrix.scale(1 / 0.4F);
                positionMatrix.scale(1 / this.twinkle2.getValue());
                positionMatrix.scale(this.twinkle3.getValue());
                positionMatrix.scale(0.7F);
                ThinGL.renderer2D().texture(positionMatrix, twinkle, 0, (twinkleHeight + 10) * (0.7F * this.twinkle3.getValue()), twinkleWidth, twinkleHeight);
            }
            case ANGRY -> {
                if (!this.globalScale.isRunning() && System.currentTimeMillis() - time + 500L >= this.emoticon.duration() && this.globalFadeOut.getTarget() == 255 && !this.globalScale.isRunning()) {
                    this.globalFadeOut.setTarget(0);
                }

                positionMatrix.scale(this.globalScale.getValue());

                final Color color = Color.fromRGBA(255, 255, 255, !this.globalFadeOut.isRunning() ? 255 : Math.round(this.globalFadeOut.getValue()));

                final Texture2D aggro = TextureManager.getInstance().getTexture("/assets/base/uis/emoticons/Emoticon_Aggro.png");

                ThinGL.renderer2D().coloredTexture(positionMatrix, aggro, 0, 0, 94, 67, color);
                positionMatrix.rotateZ((float) Math.toRadians(90));
                ThinGL.renderer2D().coloredTexture(positionMatrix, aggro, -50, -10, 94, 67, color);

                positionMatrix.translate(-94 + 94 / 2F, -82 + 67 / 2F, 0);
                positionMatrix.rotateZ((float) Math.toRadians(-210));
                positionMatrix.translate(-94 / 2F, -67 / 2F, 0);
                ThinGL.renderer2D().coloredTexture(positionMatrix, aggro, 0, 0, 94, 67, color);
            }
            case RESPOND -> {
                if (!this.globalScale.isRunning() && System.currentTimeMillis() - time + 500L >= this.emoticon.duration() && this.globalFadeOut.getTarget() == 255) {
                    this.globalFadeOut.setTarget(0);
                }

                final Color color = Color.fromRGBA(255, 255, 255, !this.globalFadeOut.isRunning() ? 255 : Math.round(this.globalFadeOut.getValue()));

                final Texture2D action = TextureManager.getInstance().getTexture("/assets/base/uis/emoticons/Emoticon_Action.png");

                ThinGL.renderer2D().coloredTexture(positionMatrix, action, 0, 0, 127, 51, color);

                positionMatrix.rotateZ((float) Math.toRadians(-20));
                ThinGL.renderer2D().coloredTexture(positionMatrix, action, 0, 81, 127, 51, color);

                positionMatrix.rotateZ((float) Math.toRadians(40));
                ThinGL.renderer2D().coloredTexture(positionMatrix, action, 0, -81, 127, 51, color);
            }
            case SURPRISED -> {
                if (!this.globalScale.isRunning() && System.currentTimeMillis() - time + 500L >= this.emoticon.duration() && this.globalFadeOut.getTarget() == 255 && !this.suprisedQM.isRunning()) {
                    this.globalFadeOut.setTarget(0);
                }

                final Texture2D em = TextureManager.getInstance().getTexture("/assets/base/uis/emoticons/Emoticon_Exclamation.png");
                final Texture2D qm = TextureManager.getInstance().getTexture("/assets/base/uis/emoticons/Emoticon_Question.png");

                positionMatrix.scale(1, this.suprisedEM.getValue(), 1);
                positionMatrix.translate(1, (1 - this.suprisedEM.getValue()) * 149, 1);
                ThinGL.renderer2D().texture(positionMatrix, em, 0, 0, 60, 149);
                positionMatrix.translate(1, -((1 - this.suprisedEM.getValue()) * 149), 1);
                positionMatrix.scale(1, 1 / this.suprisedEM.getValue(), 1);
                positionMatrix.scale(1, this.suprisedQM.getValue(), 1);
                positionMatrix.translate(1, -((1 - this.suprisedEM.getValue()) * 149), 1);
                positionMatrix.translate(1, (1 - this.suprisedQM.getValue()) * 148, 1);
                ThinGL.renderer2D().texture(positionMatrix, qm, 65, 0, 103, 148);
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
            case NOTE -> {
                if (!this.globalScale.isRunning() && System.currentTimeMillis() - this.time + 500L >= this.emoticon.duration() &&
                        this.globalFadeOut.getTarget() == 255 && this.flipCount > 3 && !this.note.isRunning()) {
                    this.globalFadeOut.setTarget(0);
                }

                if (this.noteShake.getValue() == 6 && this.flipCount % 2 == 0) {
                    this.noteShake.setTarget(-6);
                    this.flipCount++;
                } else if (this.noteShake.getValue() == -6 && this.flipCount % 2 != 0) {
                    this.noteShake.setTarget(6);
                    this.flipCount++;
                }

                final Texture2D note = TextureManager.getInstance().getTexture("/assets/base/uis/emoticons/Emoticon_Note.png");
                final Color color = Color.fromRGBA(255, 255, 255, !this.globalFadeOut.isRunning() ? 255 : Math.round(this.globalFadeOut.getValue()));
                positionMatrix.translate(this.note.getValue() + 137F / 2, 131F / 2, 0);
                positionMatrix.rotateZ((float) Math.toRadians(this.noteShake.getValue()));
                positionMatrix.translate(-137F / 2F, -131F / 2F, 0);
                ThinGL.renderer2D().coloredTexture(positionMatrix, note, 0, 0, 137, 131, color);
            }
            case SHY, HEART -> {
                if (!this.globalScale.isRunning() && System.currentTimeMillis() - this.time + 500L >= this.emoticon.duration() &&
                        this.globalFadeOut.getTarget() == 255 && this.flipCount > 3) {
                    this.globalFadeOut.setTarget(0);
                }
                if (this.shyAnimation.getValue() == 5 && this.flipCount % 2 == 0) {
                    this.shyAnimation.setTarget(-5);
                    this.flipCount++;
                } else if (this.shyAnimation.getValue() == -5 && this.flipCount % 2 != 0) {
                    this.shyAnimation.setTarget(5);
                    this.flipCount++;
                }

                boolean shy = this.emoticon.type() == Sprite.EmoticonType.SHY;

                positionMatrix.scale(this.globalScale.getValue());
                final Texture2D bubble = TextureManager.getInstance().getTexture("/assets/base/uis/emoticons/Emoticon_Balloon_N.png");
                final Texture2D texture = TextureManager.getInstance().getTexture("/assets/base/uis/emoticons/" + (shy ? "Emoticon_Shy.png" : "Emoticon_Heart.png"));

                final Color color = Color.fromRGBA(255, 255, 255, !this.globalFadeOut.isRunning() ? 255 : Math.round(this.globalFadeOut.getValue()));

                float bubbleWidth = 255, bubbleHeight = 198;
                float shyWidth = shy ? 233 : 116, shyHeight = shy ? 152 : 107;
                ThinGL.renderer2D().coloredTexture(positionMatrix, bubble, 0, 50, bubbleWidth, bubbleHeight, color);

                positionMatrix.translate(bubbleWidth / 2, 50 + bubbleHeight / 2, 0);
                positionMatrix.rotateZ((float) Math.toRadians(this.shyAnimation.getValue()));
                positionMatrix.translate(-shyWidth / 2F, -shyHeight / 2F, 0);
                ThinGL.renderer2D().coloredTexture(positionMatrix, texture, 0, -5, shyWidth, shyHeight, color);
            }
            case CHAT -> {
                if (this.chatAnimation.getValue() == 12 && this.flipCount % 2 == 0) {
                    this.chatAnimation.setTarget(-12);
                    this.flipCount++;
                } else if (this.chatAnimation.getValue() == -12 && this.flipCount % 2 != 0) {
                    this.chatAnimation.setTarget(12);
                    this.flipCount++;
                }

                if (!this.globalScale.isRunning() && System.currentTimeMillis() - this.time + 500L >= this.emoticon.duration() && this.globalFadeOut.getTarget() == 255 && this.flipCount > 3) {
                    this.globalFadeOut.setTarget(0);
                }
                positionMatrix.scale(this.globalScale.getValue());
                final Texture2D chat = TextureManager.getInstance().getTexture("/assets/base/uis/emoticons/Emoticon_Chat.png");

                final Color color = Color.fromRGBA(255, 255, 255, !this.globalFadeOut.isRunning() ? 255 : Math.round(this.globalFadeOut.getValue()));

                float chatWidth = 147, chatHeight = 205;

//                positionMatrix.translate(chatWidth / 2, chatHeight / 2, 0);
                positionMatrix.rotateZ((float) Math.toRadians(this.chatAnimation.getValue()));
                positionMatrix.translate(-chatWidth / 2F, -chatWidth / 2F, 0);
                ThinGL.renderer2D().coloredTexture(positionMatrix, chat, 0, 0, chatWidth, chatHeight, color);
            }
        }

        positionMatrix.popMatrix();
    }

    private final long time = System.currentTimeMillis();
    public boolean isFinished() {
        final Sprite.EmoticonType type = this.emoticon.type();
        if (
                type == Sprite.EmoticonType.EXCLAMATION_MARK || type == Sprite.EmoticonType.ANXIETY ||
                type == Sprite.EmoticonType.HESITATED || type == Sprite.EmoticonType.THINKING ||
                type == Sprite.EmoticonType.TWINKLE || type == Sprite.EmoticonType.SURPRISED ||
                type == Sprite.EmoticonType.RESPOND || type == Sprite.EmoticonType.NOTE
        ) {
            if (this.globalFadeOut.isRunning()) {
                return false;
            }
        }

        if (this.emoticon.type() == Sprite.EmoticonType.HESITATED || this.emoticon.type() == Sprite.EmoticonType.THINKING) {
            if (dotCount != 3) {
                return false;
            }
        }

        if (this.emoticon.type() == Sprite.EmoticonType.SHY || this.emoticon.type() == Sprite.EmoticonType.CHAT) {
            if (this.flipCount < 4) {
                return false;
            }
        }

        return System.currentTimeMillis() - time >= this.emoticon.duration();
    }
}

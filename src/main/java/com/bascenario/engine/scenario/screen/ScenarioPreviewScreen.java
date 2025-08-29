package com.bascenario.engine.scenario.screen;

import com.bascenario.engine.scenario.Scenario;
import com.bascenario.launcher.Launcher;
import com.bascenario.render.api.Screen;
import com.bascenario.render.manager.TextureManager;
import com.bascenario.util.render.RenderUtil;
import com.bascenario.util.render.FontUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.lenni0451.commons.animation.DynamicAnimation;
import net.lenni0451.commons.animation.easing.EasingFunction;
import net.lenni0451.commons.animation.easing.EasingMode;
import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.implementation.window.WindowInterface;
import net.raphimc.thingl.text.TextRun;
import org.joml.Matrix4fStack;
import org.lwjgl.opengl.GL20;

import java.io.File;

import static org.lwjgl.opengl.GL11.*;

@RequiredArgsConstructor
public class ScenarioPreviewScreen extends Screen {
    @Getter
    private final Scenario scenario;

    private final DynamicAnimation backgroundFadeIn = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_IN_OUT, 200L, 255);

    private final DynamicAnimation titleBoxFadeAnimation = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_IN_OUT, 900L, 0);
    private final DynamicAnimation titleBoxPopupAnimation = new DynamicAnimation(EasingFunction.CUBIC, EasingMode.EASE_IN_OUT, 900L, 140);

    private final DynamicAnimation titleTextFadeAnimation = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_IN_OUT, 1000L, 0);
    private DynamicAnimation titleTextPopupAnimation;
    private long finishAll = -1;

    private final DynamicAnimation finalFadeOut = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_IN_OUT, 1000L, 255);

    @Override
    public void render(Matrix4fStack positionMatrix, WindowInterface window, double mouseX, double mouseY) {
        RenderUtil.render(() -> {
            if (this.backgroundFadeIn.getValue() == 255) {
                this.backgroundFadeIn.setTarget(0);
            }

            float width = window.getFramebufferWidth(), height = window.getFramebufferHeight();

            boolean doingTheFinalFade = this.finalFadeOut.getTarget() == 0;

            final Scenario.Background previewBackground = this.scenario.getPreviewBackground();
            if (previewBackground != null && previewBackground.path() != null && !previewBackground.path().isBlank()) {
                Color color = Color.fromRGBA(255, 255, 255, doingTheFinalFade && previewBackground.fadeOut() ? Math.round(this.finalFadeOut.getValue()) : 255);
                RenderUtil.renderBackground(positionMatrix, width, height, new File(previewBackground.path()), color);

                int strength = (int) (20 * (this.finalFadeOut.getValue() / 255F));
                RenderUtil.blurRectangle(positionMatrix, 0, 0, width, height, strength);

                Color fadeeee = Color.fromRGBA(60, 60, 60, Math.round(100 * this.finalFadeOut.getValue() / 255F));
                ThinGL.renderer2D().filledRectangle(positionMatrix, 0, 0, width, height, fadeeee);
            } else {
                int alpha = doingTheFinalFade ? Math.round(this.finalFadeOut.getValue()) : 255;
                ThinGL.renderer2D().filledRectangle(positionMatrix, 0, 0, width, height, Color.fromRGBA(22, 23, 26, alpha));
            }

            final String border = previewBackground == null ? "border_non_background.png" : "border_with_background.png";
            if (doingTheFinalFade) {
                Color color = Color.fromRGBA(255, 255, 255, Math.round(100 * this.finalFadeOut.getValue() / 255F));
                RenderUtil.renderBorder(positionMatrix, width, height, "/assets/base/uis/" + border, color);
            } else {
                Color fadeeee = Color.fromRGBA(255, 255, 255, Math.round(100 * (1 - (this.backgroundFadeIn.getValue() / 255F))));
                RenderUtil.renderBorder(positionMatrix, width, height, "/assets/base/uis/" + border, fadeeee);
            }

            Color backgroundFadeColor = Color.fromRGBA(0, 0, 0, Math.round(this.backgroundFadeIn.getValue()));
            ThinGL.renderer2D().filledRectangle(positionMatrix, 0, 0, width, height, backgroundFadeColor);

            if (this.backgroundFadeIn.getValue() < 80) {
                this.titleBoxPopupAnimation.setTarget(0);
                this.titleBoxFadeAnimation.setTarget(255);
            }

            if (this.titleBoxPopupAnimation.getTarget() != 92) {
                float sizeY = height - this.titleBoxPopupAnimation.getValue();

                GL20.glEnable(GL_BLEND);
                GL20.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
                Color titleBoxFade = Color.fromRGBA(255, 255, 255, Math.max(0, Math.round(this.titleBoxFadeAnimation.getValue())));
                if (doingTheFinalFade) {
                    titleBoxFade = Color.fromRGBA(255, 255, 255, Math.max(0, Math.round(this.finalFadeOut.getValue())));
                }

                ThinGL.renderer2D().coloredTexture(positionMatrix, TextureManager.getInstance().getTexture("/assets/base/uis/title.png"),
                        0, Math.max(0, height / 2F - (sizeY / 2)), width, sizeY, titleBoxFade);
            }

            if (this.titleBoxFadeAnimation.getValue() > 180 && this.titleTextPopupAnimation == null) {
                this.titleTextPopupAnimation = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_IN_OUT, 400L, 0.85F);
                this.titleTextPopupAnimation.setTarget(1);
                this.titleTextFadeAnimation.setTarget(255);
            }

            if (this.titleTextFadeAnimation.getTarget() != 0) {
                int alpha = doingTheFinalFade ? Math.round(this.finalFadeOut.getValue()) : Math.round(this.titleTextFadeAnimation.getValue());
                final TextRun textRun = TextRun.fromString(
                        FontUtil.getFont("NotoSansSemiBold", Math.round(Math.max(0.05F * ((width + height) / 2F), 40))),
                        this.scenario.getName(),
                        Color.fromRGBA(70, 98, 150, alpha)
                );

                float scale = this.titleTextPopupAnimation.getValue();
                float textCenterX = Math.max(0, width / 2F - (ThinGL.rendererText().getExactWidth(textRun.shape()) * scale / 2));
                float textCenterY = Math.max(0, height / 2F - (ThinGL.rendererText().getExactHeight(textRun.shape()) * scale / 2)) + 5;

                positionMatrix.pushMatrix();
                positionMatrix.translate(textCenterX, textCenterY, 0);
                positionMatrix.scale(this.titleTextPopupAnimation.getValue());
                ThinGL.rendererText().textRun(positionMatrix, textRun, 0, 0);
                positionMatrix.popMatrix();

                if (!this.titleBoxFadeAnimation.isRunning() && this.finishAll == -1 && this.finalFadeOut.getTarget() == 255) {
                    this.finishAll = System.currentTimeMillis();
                }
            }

            if (this.finalFadeOut.getTarget() == 255 && this.finishAll != -1 && System.currentTimeMillis() - this.finishAll >= 1500L) {
                this.finalFadeOut.setTarget(0);
                this.finishAll = - 1;
            }

            if (this.finalFadeOut.getTarget() == 0) {
                if (this.scenario.getPreviewBackground() == null) {
                    ThinGL.renderer2D().filledRectangle(positionMatrix, 0, 0, width, height, Color.fromRGBA(0, 0, 0, Math.round(255 - this.finalFadeOut.getValue())));
                }

                if (!this.finalFadeOut.isRunning() && this.finishAll == -1) {
                    this.finishAll = System.currentTimeMillis();
                }

                if (this.finishAll != -1 && System.currentTimeMillis() - this.finishAll >= 500L) {
                    Launcher.WINDOW.setCurrentScreen(new ScenarioScreen(this.scenario));
                }
            }
        });
    }
}

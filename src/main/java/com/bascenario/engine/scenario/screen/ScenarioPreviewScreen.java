package com.bascenario.engine.scenario.screen;

import com.bascenario.engine.scenario.Scenario;
import com.bascenario.launcher.Launcher;
import com.bascenario.render.api.Screen;
import com.bascenario.render.manager.TextureManager;
import com.bascenario.util.RenderUtil;
import com.bascenario.util.render.FontUtil;
import com.bascenario.util.render.MathUtil;
import imgui.ImColor;
import imgui.ImGui;
import imgui.ImVec2;
import lombok.RequiredArgsConstructor;
import net.lenni0451.commons.animation.DynamicAnimation;
import net.lenni0451.commons.animation.easing.EasingFunction;
import net.lenni0451.commons.animation.easing.EasingMode;

import java.io.File;

@RequiredArgsConstructor
public class ScenarioPreviewScreen extends Screen {
    private final Scenario scenario;

    private final DynamicAnimation backgroundFadeIn = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_IN_OUT, 200L, 255);

    private final DynamicAnimation titleBoxFadeAnimation = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_IN_OUT, 900L, 0);
    private final DynamicAnimation titleBoxPopupAnimation = new DynamicAnimation(EasingFunction.CUBIC, EasingMode.EASE_IN_OUT, 900L, 140);

    private final DynamicAnimation titleTextFadeAnimation = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_IN_OUT, 1000L, 0);
    private DynamicAnimation titleTextPopupAnimation;
    private long finishAll = -1;

    private final DynamicAnimation finalFadeOut = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_IN_OUT, 1000L, 0);

    @Override
    public void render(double mouseX, double mouseY) {
        if (this.backgroundFadeIn.getValue() == 255) {
            this.backgroundFadeIn.setTarget(0);
        }

        ImVec2 vec = MathUtil.findBestSize(new ImVec2(width, height), new ImVec2(1920, 1080));
        int centerX = (int) Math.max(0, width / 2F - (vec.x / 2));
        int centerY = (int) Math.max(0, height / 2F - (vec.y / 2));

        final Scenario.Background previewBackground = this.scenario.getPreviewBackground();
        if (previewBackground != null && previewBackground.path() != null && !previewBackground.path().isBlank()) {
            RenderUtil.renderBackground(width, height, new File(previewBackground.path()));
        } else {
            ImGui.getForegroundDrawList().addRectFilled(new ImVec2(centerX, centerY), new ImVec2(vec.x + centerX, vec.y + centerY), ImColor.rgb(22, 23, 26));
        }

        RenderUtil.renderStartElement(width, height, "/assets/base/uis/border.png");
        int backgroundFadeColor = ImColor.rgba(0, 0, 0, Math.max(0, Math.round(backgroundFadeIn.getValue())));
        ImGui.getForegroundDrawList().addRectFilled(new ImVec2(centerX, centerY), new ImVec2(vec.x + centerX, vec.y + centerY), backgroundFadeColor);

        if (this.backgroundFadeIn.getValue() < 80) {
            this.titleBoxPopupAnimation.setTarget(0);
            this.titleBoxFadeAnimation.setTarget(255);
        }

        if (this.titleBoxPopupAnimation.getTarget() != 92) {
            ImVec2 vec1 = MathUtil.findBestSize(new ImVec2(width, height), new ImVec2(1920, 1080 - titleBoxPopupAnimation.getValue()));
            int centerX1 = (int) Math.max(0, width / 2F - (vec1.x / 2));
            int centerY1 = (int) Math.max(0, height / 2F - (vec1.y / 2));

            int titleBoxFade = ImColor.rgba(255, 255, 255, Math.max(0, Math.round(titleBoxFadeAnimation.getValue())));
            ImGui.getForegroundDrawList().addImage(TextureManager.getInstance().getTexture("/assets/base/uis/title.png"),
                    new ImVec2(centerX1, centerY1), new ImVec2(vec1.x + centerX1, vec1.y + centerY1), new ImVec2(0, 0), new ImVec2(1, 1), titleBoxFade);
        }

        if (this.titleBoxFadeAnimation.getValue() > 180 && this.titleTextPopupAnimation == null) {
            float maxSize = Math.max(0.07F * ((width + height) / 2F), 40);

            this.titleTextPopupAnimation = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_IN_OUT, 300L, Math.round(maxSize * 0.95F));
            this.titleTextPopupAnimation.setTarget(Math.round(maxSize));
            // Fuck this, I can't make it look smooth
            this.titleTextPopupAnimation.finish();
            this.titleTextFadeAnimation.setTarget(255);
        }

        if (this.titleTextFadeAnimation.getTarget() != 0) {
            ImGui.pushFont(FontUtil.getFont("NotoSansSemiBold", Math.round(this.titleTextPopupAnimation.getTarget())));

            ImVec2 size = ImGui.calcTextSize(this.scenario.getName());
            int textCenterX = (int) Math.max(0, width / 2F - (size.x / 2));
            int textCenterY = (int) Math.max(0, height / 2F - (size.y / 2));

            ImGui.getForegroundDrawList().addText(new ImVec2(textCenterX, textCenterY), ImColor.rgba(70, 98, 150, Math.round(this.titleTextFadeAnimation.getValue())), this.scenario.getName());
            ImGui.popFont();
            if (!this.titleBoxFadeAnimation.isRunning() && this.finishAll == -1 && this.finalFadeOut.getTarget() == 0) {
                this.finishAll = System.currentTimeMillis();
            }
        }

        if (this.finalFadeOut.getTarget() == 0 && this.finishAll != -1 && System.currentTimeMillis() - this.finishAll >= 1500L) {
            this.finalFadeOut.setTarget(255);
            this.finishAll = - 1;
        }

        if (this.finalFadeOut.getTarget() == 255) {
            ImGui.getForegroundDrawList().addRectFilled(new ImVec2(0, 0), new ImVec2(width, height), ImColor.rgba(0, 0, 0, Math.round(this.finalFadeOut.getValue())));

            if (!this.finalFadeOut.isRunning() && this.finishAll == -1) {
                this.finishAll = System.currentTimeMillis();
            }

            if (this.finishAll != -1 && System.currentTimeMillis() - this.finishAll >= 500L) {
                Launcher.WINDOW.setCurrentScreen(new ScenarioScreen(this.scenario));
            }
        }
    }
}

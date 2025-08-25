package com.bascenario.engine.scenario.screen;

import com.bascenario.engine.scenario.Scenario;
import com.bascenario.render.api.Screen;
import com.bascenario.util.RenderUtil;
import imgui.ImColor;
import imgui.ImGui;
import lombok.RequiredArgsConstructor;
import net.lenni0451.commons.animation.DynamicAnimation;
import net.lenni0451.commons.animation.easing.EasingFunction;
import net.lenni0451.commons.animation.easing.EasingMode;

import java.io.File;

@RequiredArgsConstructor
public class ScenarioScreen extends Screen {
    private final Scenario scenario;
    private final long startTime = System.currentTimeMillis();
    // We want to keep track of the current background :P;
    private Scenario.Background background, queueBackground;
    private DynamicAnimation backgroundFadeIn, backgroundFadeOut;

    @Override
    public void render(double mouseX, double mouseY) {
        this.pollBackground();

        if (this.background != null) {
            RenderUtil.renderBackground(width, height, new File(this.background.path()));
        }
        if (this.backgroundFadeOut != null) {
            ImGui.getForegroundDrawList().addRectFilled(0, 0, width, height, ImColor.rgba(0, 0, 0, Math.round(this.backgroundFadeOut.getValue())));
        }
        if (this.backgroundFadeIn != null) {
            ImGui.getForegroundDrawList().addRectFilled(0, 0, width, height, ImColor.rgba(0, 0, 0, Math.round(this.backgroundFadeIn.getValue())));
        }
    }

    private void pollBackground() {
        if (this.backgroundFadeIn != null && !this.backgroundFadeIn.isRunning()) {
            this.backgroundFadeIn = null;
        }

        if (this.backgroundFadeOut != null && !this.backgroundFadeOut.isRunning() && this.queueBackground != null) {
            updateBackground(this.queueBackground);
            this.queueBackground = null;
            this.backgroundFadeOut = null;
        }

        Scenario.Background selected = this.background;
        for (Scenario.Background background : scenario.getBackgrounds()) {
            if (background.start() > duration() || background == this.background) {
                continue;
            }

            selected = background;
        }

        if (this.background != null && this.background.end() > 0 && duration() > this.background.end()) {
            selected = null;
        }

        if (this.background == selected) {
            return;
        }

        if (this.background != null && this.background.fadeOut()) {
            this.queueBackground = selected;
            this.backgroundFadeOut = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_OUT, 500L, 0);
            this.backgroundFadeOut.setTarget(255);
            return;
        }

        updateBackground(selected);
    }

    private void updateBackground(Scenario.Background background) {
        this.background = background;
        if (!this.background.fadeIn()) {
            return;
        }

        this.backgroundFadeIn = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_IN, 500L, 255);
        this.backgroundFadeIn.setTarget(0);
    }

    private long duration() {
        return System.currentTimeMillis() - this.startTime;
    }
}

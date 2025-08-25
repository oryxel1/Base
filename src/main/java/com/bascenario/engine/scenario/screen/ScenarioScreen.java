package com.bascenario.engine.scenario.screen;

import com.bascenario.engine.scenario.Scenario;
import com.bascenario.render.api.Screen;
import com.bascenario.util.RenderUtil;
import lombok.RequiredArgsConstructor;

import java.io.File;

@RequiredArgsConstructor
public class ScenarioScreen extends Screen {
    private final Scenario scenario;
    private final long startTime = System.currentTimeMillis();
    // We want to keep track of the current background :P;
    private Scenario.Background background;

    @Override
    public void render(double mouseX, double mouseY) {
        this.pollBackground();

        if (this.background != null) {
            RenderUtil.renderBackground(width, height, new File(this.background.path()));
        }
    }

    private void pollBackground() {
        if (this.background != null && this.background.end() > 0 && duration() > this.background.end()) {
            this.background = null;
        }

        for (Scenario.Background background : scenario.getBackgrounds()) {
            if (background.start() > duration() || background == this.background) {
                continue;
            }

            this.background = background;
        }
    }

    private long duration() {
        return System.currentTimeMillis() - this.startTime;
    }
}

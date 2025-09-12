package com.bascenario.render.scenario.extra;

import com.bascenario.engine.scenario.Scenario;
import com.bascenario.Launcher;
import com.bascenario.render.api.Screen;
import com.bascenario.render.scenario.ScenarioPreviewScreen;
import lombok.RequiredArgsConstructor;
import net.raphimc.thingl.implementation.window.WindowInterface;
import org.joml.Matrix4fStack;

@RequiredArgsConstructor
public class ScenarioPlayDelay extends Screen {
    private final Scenario scenario;
    private final long start = System.currentTimeMillis();

    @Override
    public void render(Matrix4fStack positionMatrix, WindowInterface window, double mouseX, double mouseY) {
        if (System.currentTimeMillis() - this.start > 3000L) {
            Launcher.WINDOW.setCurrentScreen(new ScenarioPreviewScreen(scenario));
        }
    }
}

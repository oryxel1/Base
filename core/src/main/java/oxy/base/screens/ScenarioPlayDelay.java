package oxy.base.screens;

import lombok.RequiredArgsConstructor;
import oxy.base.api.Scenario;
import oxy.base.utils.ExtendableScreen;
import oxy.base.utils.Launcher;

@RequiredArgsConstructor
public class ScenarioPlayDelay extends ExtendableScreen {
    private final Scenario scenario;
    private final long start = System.currentTimeMillis();

    @Override
    public void render(float delta) {
        if (System.currentTimeMillis() - this.start > 5000L) {
            Launcher.WINDOW.setScreen(new ScenarioScreen(scenario));
        }
    }
}
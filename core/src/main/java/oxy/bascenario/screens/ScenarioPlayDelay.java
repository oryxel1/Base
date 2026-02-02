package oxy.bascenario.screens;

import lombok.RequiredArgsConstructor;
import oxy.bascenario.api.Scenario;
import oxy.bascenario.utils.ExtendableScreen;
import oxy.bascenario.utils.Launcher;

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
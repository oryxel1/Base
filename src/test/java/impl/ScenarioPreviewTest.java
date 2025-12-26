package impl;

import oxy.bascenario.api.event.element.AddElementEvent;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.api.render.elements.Preview;
import oxy.bascenario.screens.ScenarioScreen;
import oxy.bascenario.utils.Launcher;
import oxy.bascenario.api.Scenario;

public class ScenarioPreviewTest {
    public static void main(String[] args) {
        final Scenario.Builder scenario = new Scenario.Builder();
        scenario.add(0, new AddElementEvent(0, new Preview("Scenario Preview Test", "Episode: 1", null), RenderLayer.TOP));

        Launcher.launch(new ScenarioScreen(scenario.build()), false);
    }
}

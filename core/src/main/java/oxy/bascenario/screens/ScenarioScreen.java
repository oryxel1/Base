package oxy.bascenario.screens;

import lombok.RequiredArgsConstructor;
import oxy.bascenario.api.Scenario;
import oxy.bascenario.utils.ExtendableScreen;

@RequiredArgsConstructor
public class ScenarioScreen extends ExtendableScreen {
    private final Scenario scenario;
}

package com.bascenario.engine.scenario.screen;

import com.bascenario.engine.scenario.Scenario;
import com.bascenario.render.api.Screen;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ScenarioScreen extends Screen {
    private final Scenario scenario;
}

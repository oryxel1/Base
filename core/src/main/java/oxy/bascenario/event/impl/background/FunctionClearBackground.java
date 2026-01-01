package oxy.bascenario.event.impl.background;

import oxy.bascenario.api.event.background.ClearBackgroundEvent;
import oxy.bascenario.event.base.FunctionEvent;
import oxy.bascenario.screens.ScenarioScreen;

public class FunctionClearBackground extends FunctionEvent<ClearBackgroundEvent> {
    public FunctionClearBackground(ClearBackgroundEvent event) {
        super(event);
    }

    @Override
    public void run(ScenarioScreen screen) {
        screen.clearBackground(event.duration());
    }
}

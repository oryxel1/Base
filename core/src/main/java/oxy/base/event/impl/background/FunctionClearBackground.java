package oxy.base.event.impl.background;

import oxy.base.api.event.background.ClearBackgroundEvent;
import oxy.base.event.base.FunctionEvent;
import oxy.base.screens.ScenarioScreen;

public class FunctionClearBackground extends FunctionEvent<ClearBackgroundEvent> {
    public FunctionClearBackground(ClearBackgroundEvent event) {
        super(event);
    }

    @Override
    public void run(ScenarioScreen screen) {
        screen.clearBackground(event.duration());
    }
}

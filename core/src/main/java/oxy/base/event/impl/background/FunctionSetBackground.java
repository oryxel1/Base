package oxy.base.event.impl.background;

import oxy.base.api.event.background.SetBackgroundEvent;
import oxy.base.event.base.FunctionEvent;
import oxy.base.screens.ScenarioScreen;

public class FunctionSetBackground extends FunctionEvent<SetBackgroundEvent> {
    public FunctionSetBackground(SetBackgroundEvent event) {
        super(event);
    }

    @Override
    public void run(ScenarioScreen screen) {
        screen.background(event.background(), event.duration());
    }
}

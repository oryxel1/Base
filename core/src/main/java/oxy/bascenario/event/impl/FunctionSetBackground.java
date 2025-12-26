package oxy.bascenario.event.impl;

import oxy.bascenario.api.event.SetBackgroundEvent;
import oxy.bascenario.event.base.FunctionEvent;
import oxy.bascenario.screens.ScenarioScreen;

public class FunctionSetBackground extends FunctionEvent<SetBackgroundEvent> {
    public FunctionSetBackground(SetBackgroundEvent event) {
        super(event);
    }

    @Override
    public void run(ScenarioScreen screen) {
//        screen.setBackground(event.getBackground());
    }
}

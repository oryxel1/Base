package oxy.bascenario.event.impl;

import oxy.bascenario.api.event.ShowButtonsEvent;
import oxy.bascenario.event.base.FunctionEvent;
import oxy.bascenario.screens.ScenarioScreen;

public class FunctionShowButtons extends FunctionEvent<ShowButtonsEvent> {
    public FunctionShowButtons(ShowButtonsEvent event) {
        super(event);
    }

    @Override
    public void run(ScenarioScreen screen) {
        screen.setShowButtons(event.show());
    }
}

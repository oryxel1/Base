package oxy.base.event.impl;

import oxy.base.api.event.ShowButtonsEvent;
import oxy.base.event.base.FunctionEvent;
import oxy.base.screens.ScenarioScreen;

public class FunctionShowButtons extends FunctionEvent<ShowButtonsEvent> {
    public FunctionShowButtons(ShowButtonsEvent event) {
        super(event);
    }

    @Override
    public void run(ScenarioScreen screen) {
        screen.setShowButtons(event.show());
    }
}

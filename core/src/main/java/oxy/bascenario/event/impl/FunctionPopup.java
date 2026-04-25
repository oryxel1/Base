package oxy.bascenario.event.impl;

import oxy.bascenario.api.event.PopupEvent;
import oxy.bascenario.event.base.FunctionEvent;
import oxy.bascenario.screens.ScenarioScreen;

public class FunctionPopup extends FunctionEvent<PopupEvent> {
    public FunctionPopup(PopupEvent event) {
        super(event);
    }

    @Override
    public void run(ScenarioScreen screen) {
        screen.setPopup(event.type() == PopupEvent.Type.CLEAR ? null : event.popup());
    }
}

package oxy.base.event.impl;

import oxy.base.api.event.PopupEvent;
import oxy.base.event.base.FunctionEvent;
import oxy.base.screens.ScenarioScreen;

public class FunctionPopup extends FunctionEvent<PopupEvent> {
    public FunctionPopup(PopupEvent event) {
        super(event);
    }

    @Override
    public void run(ScenarioScreen screen) {
        screen.setPopup(event.type() == PopupEvent.Type.CLEAR ? null : event.popup());
    }
}

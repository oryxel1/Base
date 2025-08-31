package com.bascenario.engine.scenario.event.impl;

import com.bascenario.engine.scenario.elements.PopupImage;
import com.bascenario.engine.scenario.event.api.Event;
import com.bascenario.engine.scenario.screen.ScenarioScreen;

public class SetPopupEvent extends Event {
    private final PopupImage popupImage;
    public SetPopupEvent(PopupImage popupImage) {
        super(0);
        this.popupImage = popupImage;
    }

    @Override
    public void onStart(ScenarioScreen screen) {
        screen.setPopupImage(this.popupImage);
    }
}

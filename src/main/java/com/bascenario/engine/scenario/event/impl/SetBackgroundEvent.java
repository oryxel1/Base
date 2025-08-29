package com.bascenario.engine.scenario.event.impl;

import com.bascenario.engine.scenario.elements.Background;
import com.bascenario.engine.scenario.event.api.Event;
import com.bascenario.engine.scenario.screen.ScenarioScreen;

public class SetBackgroundEvent extends Event {
    private final Background background;
    public SetBackgroundEvent(Background background) {
        super(0);
        this.background = background;
    }

    @Override
    public void onStart(ScenarioScreen screen) {
        screen.setBackground(this.background, false);
    }
}

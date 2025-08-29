package com.bascenario.engine.scenario.event.impl;

import com.bascenario.engine.scenario.event.api.Event;
import com.bascenario.engine.scenario.screen.ScenarioScreen;

public class DialogueLockEvent extends Event {
    private final boolean lock;
    public DialogueLockEvent(boolean lock) {
        super(0);
        this.lock = lock;
    }

    @Override
    public void onStart(ScenarioScreen screen) {
        screen.setLockClick(this.lock);
    }
}

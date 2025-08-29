package com.bascenario.engine.scenario.event.impl.lock;

import com.bascenario.engine.scenario.event.api.Event;
import com.bascenario.engine.scenario.screen.ScenarioScreen;

public class LockClickEvent extends Event {
    private final boolean lock;
    public LockClickEvent(boolean lock) {
        super(0);
        this.lock = lock;
    }

    @Override
    public void onStart(ScenarioScreen screen) {
        screen.setLockClick(this.lock);
    }
}

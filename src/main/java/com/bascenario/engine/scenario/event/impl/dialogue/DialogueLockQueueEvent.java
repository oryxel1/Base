package com.bascenario.engine.scenario.event.impl.dialogue;

import com.bascenario.engine.scenario.event.api.Event;
import com.bascenario.engine.scenario.screen.ScenarioScreen;

public class DialogueLockQueueEvent extends Event {
    private final boolean lock;
    public DialogueLockQueueEvent(long duration, boolean lock) {
        super(duration);
        this.lock = lock;
    }

    @Override
    public void onEnd(ScenarioScreen screen) {
        screen.setLockClick(this.lock);
    }
}

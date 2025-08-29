package com.bascenario.engine.scenario.event.impl.dialogue;

import com.bascenario.engine.scenario.event.api.Event;
import com.bascenario.engine.scenario.screen.ScenarioScreen;

public class RedirectDialogueEvent extends Event {
    private final int index;
    public RedirectDialogueEvent(int index) {
        super(1);
        this.index = index;
    }

    @Override
    public void onStart(ScenarioScreen screen) {
        screen.setDialogueIndex(this.index);
    }
}

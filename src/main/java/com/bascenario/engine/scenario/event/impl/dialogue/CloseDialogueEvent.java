package com.bascenario.engine.scenario.event.impl.dialogue;

import com.bascenario.engine.scenario.event.api.Event;
import com.bascenario.engine.scenario.screen.ScenarioScreen;

public class CloseDialogueEvent extends Event {
    public CloseDialogueEvent() {
        super(0);
    }

    @Override
    public void onStart(ScenarioScreen screen) {
        screen.setDialogue(null);
    }
}

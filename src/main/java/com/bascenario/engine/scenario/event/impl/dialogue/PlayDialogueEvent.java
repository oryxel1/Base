package com.bascenario.engine.scenario.event.impl.dialogue;

import com.bascenario.engine.scenario.elements.Dialogue;
import com.bascenario.engine.scenario.event.api.Event;
import com.bascenario.engine.scenario.render.DialogueRender;
import com.bascenario.engine.scenario.screen.ScenarioScreen;

public class PlayDialogueEvent extends Event {
    private final Dialogue dialogue;
    public PlayDialogueEvent(Dialogue dialogue) {
        super(0);
        this.dialogue = dialogue;
    }

    @Override
    public void onStart(ScenarioScreen screen) {
        if (screen.getDialogueIndex() != dialogue.index()) {
            return;
        }

        screen.setDialogue(new DialogueRender(this.dialogue));
    }
}

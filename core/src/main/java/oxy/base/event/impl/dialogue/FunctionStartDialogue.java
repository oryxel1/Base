package oxy.base.event.impl.dialogue;

import oxy.base.api.event.dialogue.StartDialogueEvent;
import oxy.base.event.base.FunctionEvent;
import oxy.base.screens.ScenarioScreen;

public class FunctionStartDialogue extends FunctionEvent<StartDialogueEvent> {
    public FunctionStartDialogue(StartDialogueEvent event) {
        super(event);
    }

    @Override
    public void run(ScenarioScreen screen) {
        if (screen.getDialogueRenderer().start(event.offset(), event.type(), event.index(), event.name(), event.association(), event.background(), event.dialogues())) {
            screen.setBusyDialogue(true);
        }
    }
}

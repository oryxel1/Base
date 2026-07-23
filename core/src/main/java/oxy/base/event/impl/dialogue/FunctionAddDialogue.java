package oxy.base.event.impl.dialogue;

import oxy.base.api.event.dialogue.AddDialogueEvent;
import oxy.base.event.base.FunctionEvent;
import oxy.base.screens.ScenarioScreen;

public class FunctionAddDialogue extends FunctionEvent<AddDialogueEvent> {
    public FunctionAddDialogue(AddDialogueEvent event) {
        super(event);
    }

    @Override
    public void run(ScenarioScreen screen) {
        if (screen.getDialogueRenderer().add(event.index(), event.newLine(), event.dialogues())) {
            screen.setBusyDialogue(true);
        }
    }
}

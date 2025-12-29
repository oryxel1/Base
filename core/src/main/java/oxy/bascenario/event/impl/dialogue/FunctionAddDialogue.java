package oxy.bascenario.event.impl.dialogue;

import oxy.bascenario.api.event.dialogue.AddDialogueEvent;
import oxy.bascenario.event.base.FunctionEvent;
import oxy.bascenario.screens.ScenarioScreen;

public class FunctionAddDialogue extends FunctionEvent<AddDialogueEvent> {
    public FunctionAddDialogue(AddDialogueEvent event) {
        super(event);
    }

    @Override
    public void run(ScenarioScreen screen) {
        screen.getDialogueRenderer().add(event.index(), event.dialogues());
        screen.setBusyDialogue(true);
    }
}

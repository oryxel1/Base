package oxy.bascenario.event.impl.dialogue;

import oxy.bascenario.api.event.dialogue.StartDialogueEvent;
import oxy.bascenario.event.base.FunctionEvent;
import oxy.bascenario.screens.ScenarioScreen;

public class FunctionStartDialogue extends FunctionEvent<StartDialogueEvent> {
    public FunctionStartDialogue(StartDialogueEvent event) {
        super(event);
    }

    @Override
    public void run(ScenarioScreen screen) {
        screen.getDialogueRenderer().start(event.type(), event.index(), event.name(), event.association(), event.background(), event.dialogues());
        screen.setBusyDialogue(true);
    }
}

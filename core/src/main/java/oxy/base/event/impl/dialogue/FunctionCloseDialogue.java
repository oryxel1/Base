package oxy.base.event.impl.dialogue;

import oxy.base.api.event.dialogue.CloseDialogueEvent;
import oxy.base.event.base.FunctionEvent;
import oxy.base.screens.ScenarioScreen;

public class FunctionCloseDialogue extends FunctionEvent<CloseDialogueEvent> {
    public FunctionCloseDialogue(CloseDialogueEvent event) {
        super(event);
    }

    @Override
    public void run(ScenarioScreen screen) {
        screen.getDialogueRenderer().stop();
    }
}

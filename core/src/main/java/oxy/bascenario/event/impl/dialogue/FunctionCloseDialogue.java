package oxy.bascenario.event.impl.dialogue;

import oxy.bascenario.api.event.dialogue.CloseDialogueEvent;
import oxy.bascenario.event.base.FunctionEvent;
import oxy.bascenario.screens.ScenarioScreen;

public class FunctionCloseDialogue extends FunctionEvent<CloseDialogueEvent> {
    public FunctionCloseDialogue(CloseDialogueEvent event) {
        super(event);
    }

    @Override
    public void start(ScenarioScreen screen) {
        screen.getDialogueRenderer().stop();

    }
}

package oxy.bascenario.event.impl.dialogue;

import oxy.bascenario.api.event.dialogue.CloseDialogueEvent;
import oxy.bascenario.event.base.FunctionEvent;
import oxy.bascenario.screens.ScenarioScreen;

public class FunctionCloseOptions extends FunctionEvent<CloseDialogueEvent> {
    public FunctionCloseOptions(CloseDialogueEvent event) {
        super(event);
    }

    @Override
    public void run(ScenarioScreen screen) {
        screen.getOptionsRenderer().setOptions(null, null);
    }
}

package oxy.base.event.impl.dialogue;

import oxy.base.api.event.dialogue.CloseDialogueEvent;
import oxy.base.event.base.FunctionEvent;
import oxy.base.screens.ScenarioScreen;

public class FunctionCloseOptions extends FunctionEvent<CloseDialogueEvent> {
    public FunctionCloseOptions(CloseDialogueEvent event) {
        super(event);
    }

    @Override
    public void run(ScenarioScreen screen) {
        screen.getOptionsRenderer().setOptions(null, null);
    }
}

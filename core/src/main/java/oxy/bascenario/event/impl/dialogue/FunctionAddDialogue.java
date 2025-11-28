package oxy.bascenario.event.impl.dialogue;

import oxy.bascenario.api.render.elements.Dialogue;
import oxy.bascenario.api.event.dialogue.AddDialogueEvent;
import oxy.bascenario.event.base.FunctionEvent;
import oxy.bascenario.screens.ScenarioScreen;

public class FunctionAddDialogue extends FunctionEvent<AddDialogueEvent> {
    public FunctionAddDialogue(AddDialogueEvent event) {
        super(event);
    }

    @Override
    public void run(ScenarioScreen screen) {
        for (Dialogue dialogue : event().getDialogues()) {
            screen.getDialogueRenderer().add(event.getIndex(), dialogue);
        }
        screen.setBusyDialogue(true);
    }
}

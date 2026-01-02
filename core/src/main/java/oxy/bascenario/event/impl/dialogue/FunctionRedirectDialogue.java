package oxy.bascenario.event.impl.dialogue;

import oxy.bascenario.api.event.dialogue.RedirectDialogueEvent;
import oxy.bascenario.event.base.FunctionEvent;
import oxy.bascenario.screens.ScenarioScreen;

public class FunctionRedirectDialogue extends FunctionEvent<RedirectDialogueEvent> {
    public FunctionRedirectDialogue(RedirectDialogueEvent event) {
        super(event);
    }

    @Override
    public void run(ScenarioScreen screen) {
        screen.getDialogueRenderer().setCurrentIndex(event.index());
    }
}

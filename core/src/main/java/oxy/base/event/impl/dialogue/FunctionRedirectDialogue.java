package oxy.base.event.impl.dialogue;

import oxy.base.api.event.dialogue.RedirectDialogueEvent;
import oxy.base.event.base.FunctionEvent;
import oxy.base.screens.ScenarioScreen;

public class FunctionRedirectDialogue extends FunctionEvent<RedirectDialogueEvent> {
    public FunctionRedirectDialogue(RedirectDialogueEvent event) {
        super(event);
    }

    @Override
    public void run(ScenarioScreen screen) {
        screen.getDialogueRenderer().setCurrentIndex(event.index());
    }
}

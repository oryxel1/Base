package oxy.bascenario.event.impl.dialogue;

import oxy.bascenario.api.event.dialogue.ShowQuestionSelectionEvent;
import oxy.bascenario.event.base.FunctionEvent;
import oxy.bascenario.screens.ScenarioScreen;

public class FunctionShowQuestionSelection extends FunctionEvent<ShowQuestionSelectionEvent> {
    public FunctionShowQuestionSelection(ShowQuestionSelectionEvent event) {
        super(event);
    }

    @Override
    public void run(ScenarioScreen screen) {
        screen.getQuestionSelectionRenderer().setValues(event.type(), event().question(), event.answers());
        screen.setBusyAnswerSelection(true);
    }
}

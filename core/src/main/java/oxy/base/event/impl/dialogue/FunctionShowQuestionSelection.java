package oxy.base.event.impl.dialogue;

import oxy.base.api.event.dialogue.ShowQuestionSelectionEvent;
import oxy.base.event.base.FunctionEvent;
import oxy.base.screens.ScenarioScreen;

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

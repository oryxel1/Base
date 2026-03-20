package oxy.bascenario.event.impl.dialogue;

import oxy.bascenario.api.event.dialogue.ShowOptionsEvent;
import oxy.bascenario.event.base.FunctionEvent;
import oxy.bascenario.screens.ScenarioScreen;

public class FunctionShowOptions extends FunctionEvent<ShowOptionsEvent> {
    public FunctionShowOptions(ShowOptionsEvent event) {
        super(event);
    }

    @Override
    public void run(ScenarioScreen screen) {
        screen.getOptionsRenderer().setOptions(event.options());
        screen.setBusyOptions(true);
    }
}

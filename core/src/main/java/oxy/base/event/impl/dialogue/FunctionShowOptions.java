package oxy.base.event.impl.dialogue;

import oxy.base.api.event.dialogue.ShowOptionsEvent;
import oxy.base.event.base.FunctionEvent;
import oxy.base.screens.ScenarioScreen;

public class FunctionShowOptions extends FunctionEvent<ShowOptionsEvent> {
    public FunctionShowOptions(ShowOptionsEvent event) {
        super(event);
    }

    @Override
    public void run(ScenarioScreen screen) {
        screen.getOptionsRenderer().setOptions(event.type(), event.options());
        screen.setBusyOptions(true);
    }
}

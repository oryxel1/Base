package oxy.bascenario.event.impl.log;

import oxy.bascenario.api.event.element.ClearLogEvent;
import oxy.bascenario.event.base.FunctionEvent;
import oxy.bascenario.screens.ScenarioScreen;

public class FunctionClearLog extends FunctionEvent<ClearLogEvent> {
    public FunctionClearLog(ClearLogEvent event) {
        super(event);
    }

    @Override
    public void run(ScenarioScreen screen) {
        screen.getLogRenderer().stop();
    }
}

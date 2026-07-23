package oxy.base.event.impl.log;

import oxy.base.api.event.element.ClearLogEvent;
import oxy.base.event.base.FunctionEvent;
import oxy.base.screens.ScenarioScreen;

public class FunctionClearLog extends FunctionEvent<ClearLogEvent> {
    public FunctionClearLog(ClearLogEvent event) {
        super(event);
    }

    @Override
    public void run(ScenarioScreen screen) {
        screen.getLogRenderer().stop();
    }
}

package oxy.bascenario.event.impl.log;

import oxy.bascenario.api.event.log.AddLogEvent;
import oxy.bascenario.event.base.FunctionEvent;
import oxy.bascenario.screens.ScenarioScreen;

public class FunctionAddLog extends FunctionEvent<AddLogEvent> {
    public FunctionAddLog(AddLogEvent event) {
        super(event);
    }

    @Override
    public void run(ScenarioScreen screen) {
        if (event.type() == AddLogEvent.Type.CLEAR) {
            screen.getLogRenderer().start(event.index(), event.offset(), event.dialogues());
        } else {
            screen.getLogRenderer().add(event.index(), true, event.dialogues());
        }
    }
}

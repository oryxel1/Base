package oxy.base.event.impl.log;

import oxy.base.api.event.log.AddLogEvent;
import oxy.base.event.base.FunctionEvent;
import oxy.base.screens.ScenarioScreen;

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

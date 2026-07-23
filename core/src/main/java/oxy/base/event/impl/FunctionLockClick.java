package oxy.base.event.impl;

import oxy.base.api.event.LockClickEvent;
import oxy.base.event.base.FunctionEvent;
import oxy.base.screens.ScenarioScreen;

public class FunctionLockClick extends FunctionEvent<LockClickEvent> {
    public FunctionLockClick(LockClickEvent event) {
        super(event);
    }

    @Override
    public void run(ScenarioScreen screen) {
        screen.setLockClick(event().lock());
    }
}

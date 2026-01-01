package oxy.bascenario.event.impl;

import oxy.bascenario.api.event.LockClickEvent;
import oxy.bascenario.event.base.FunctionEvent;
import oxy.bascenario.screens.ScenarioScreen;

public class FunctionLockClick extends FunctionEvent<LockClickEvent> {
    public FunctionLockClick(LockClickEvent event) {
        super(event);
    }

    @Override
    public void run(ScenarioScreen screen) {
        screen.setLockClick(event().lock());
    }
}

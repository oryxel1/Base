package oxy.base.event.base;

import lombok.RequiredArgsConstructor;
import oxy.base.api.event.api.Event;
import oxy.base.screens.ScenarioScreen;

@RequiredArgsConstructor
public abstract class FunctionEvent<T extends Event> {
    protected final T event;
    public final T event() {
        return event;
    }

    public void run(ScenarioScreen screen) {}
}

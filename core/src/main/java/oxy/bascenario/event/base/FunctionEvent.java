package oxy.bascenario.event.base;

import lombok.RequiredArgsConstructor;
import oxy.bascenario.api.event.api.Event;
import oxy.bascenario.screens.ScenarioScreen;

@RequiredArgsConstructor
public abstract class FunctionEvent<T extends Event<?>> {
    protected final T event;
    public final T event() {
        return event;
    }

    public void run(ScenarioScreen screen) {}
}

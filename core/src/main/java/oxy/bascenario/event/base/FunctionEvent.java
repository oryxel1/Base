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

    private final long start = System.currentTimeMillis();

    public void start(ScenarioScreen screen) {}
    public void end(ScenarioScreen screen) {}
    public void render(ScenarioScreen screen) {}

    public final boolean finished() {
        return System.currentTimeMillis() - this.start >= this.event.getDuration();
    }
}

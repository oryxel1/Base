package oxy.bascenario.event;

import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import oxy.bascenario.api.event.Event;
import oxy.bascenario.api.event.RenderEvent;
import oxy.bascenario.screens.ScenarioScreen;

@RequiredArgsConstructor
public abstract class EventFunction<T extends Event<?>> {
    protected final T event;
    public final T event() {
        return event;
    }

    private final long start = System.currentTimeMillis();

    public void start(ScenarioScreen screen) {}
    public void end(ScenarioScreen screen) {}
    public void render(ScenarioScreen screen) {}

    public void serialize(JsonObject serialized) {
        serialized.addProperty("duration", event.getDuration());
        if (event instanceof RenderEvent<?> render) {
            serialized.addProperty("render-layer", render.layer().name());
        }
    }
    public abstract T deserialize(JsonObject serialized);

    public final boolean finished() {
        return System.currentTimeMillis() - this.start >= this.event.getDuration();
    }
}

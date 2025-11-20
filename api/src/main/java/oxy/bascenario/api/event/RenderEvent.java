package oxy.bascenario.api.event;

import oxy.bascenario.api.render.RenderLayer;

public abstract class RenderEvent<T> extends Event<T> {
    private final RenderLayer layer;
    public RenderEvent(RenderLayer layer, long duration) {
        super(duration);
        this.layer = layer;
    }

    public final RenderLayer layer() {
        return layer;
    }

    public static boolean is(Event<?> event, RenderLayer layer) {
        return event instanceof RenderEvent<?> render && render.layer == layer;
    }
}

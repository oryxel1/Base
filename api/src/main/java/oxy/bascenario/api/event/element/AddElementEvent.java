package oxy.bascenario.api.event.element;

import oxy.bascenario.api.event.api.Event;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.api.utils.math.Vec2;

public record AddElementEvent(int id, Vec2 position, Object element, RenderLayer layer) implements Event {
    public AddElementEvent(int id, Object element, RenderLayer layer) {
        this(id, new Vec2(0, 0), element, layer);
    }
}

package oxy.base.api.event.element;

import oxy.base.api.event.api.Event;
import oxy.base.api.render.RenderLayer;
import oxy.base.api.utils.math.Vec2;

public record AddElementEvent(int id, Vec2 position, Object element, RenderLayer layer) implements Event {
    public AddElementEvent(int id, Object element, RenderLayer layer) {
        this(id, new Vec2(0, 0), element, layer);
    }
}

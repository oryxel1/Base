package oxy.bascenario.api.event.element;

import oxy.bascenario.api.event.api.Event;
import oxy.bascenario.api.render.RenderLayer;

public record AddElementEvent(int id, Object element, RenderLayer layer) implements Event {
}

package oxy.bascenario.api.event.element;

import oxy.bascenario.api.event.api.Event;

public record AttachElementEvent(int id, int subId, Object element) implements Event {
}

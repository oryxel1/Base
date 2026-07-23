package oxy.base.api.event.element;

import oxy.base.api.event.api.Event;

public record AttachElementEvent(int id, int subId, Object element) implements Event {
}

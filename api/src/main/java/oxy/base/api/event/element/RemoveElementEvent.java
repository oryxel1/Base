package oxy.base.api.event.element;

import oxy.base.api.event.api.Event;

public record RemoveElementEvent(int id, Integer subId) implements Event {
    public RemoveElementEvent(int id) {
        this(id, null);
    }
}

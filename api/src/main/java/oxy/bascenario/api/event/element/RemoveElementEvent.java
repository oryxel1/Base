package oxy.bascenario.api.event.element;

import oxy.bascenario.api.event.api.Event;

public record RemoveElementEvent(int id, Integer subId) implements Event {
    public RemoveElementEvent(int id) {
        this(id, null);
    }
}

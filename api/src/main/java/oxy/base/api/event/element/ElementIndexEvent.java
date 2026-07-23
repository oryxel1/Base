package oxy.base.api.event.element;

import oxy.base.api.event.api.Event;

public record ElementIndexEvent(int mainIndex, Integer subIndex, int newIndex, boolean swap) implements Event {
    public ElementIndexEvent(int index, int newIndex, boolean swap) {
        this(index, null, newIndex, swap);
    }
}

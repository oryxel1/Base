package oxy.base.api.event;

import oxy.base.api.event.api.Event;

public record ShowButtonsEvent(boolean show) implements Event {
}

package oxy.bascenario.api.event;

import oxy.bascenario.api.event.api.Event;

public record ShowButtonsEvent(boolean show) implements Event {
}

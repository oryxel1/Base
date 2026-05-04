package oxy.bascenario.api.event.utility;

import oxy.bascenario.api.event.api.Event;

public record CheckForDialogueEvent(int index, Event... events) implements Event {
}

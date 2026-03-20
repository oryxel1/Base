package oxy.bascenario.api.event.dialogue;

import oxy.bascenario.api.event.api.Event;

import java.util.Map;

public record ShowOptionsEvent(Map<String, Integer> options) implements Event {
}

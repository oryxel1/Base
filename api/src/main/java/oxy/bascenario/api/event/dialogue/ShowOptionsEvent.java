package oxy.bascenario.api.event.dialogue;

import oxy.bascenario.api.event.api.Event;
import oxy.bascenario.api.render.elements.text.font.FontType;

import java.util.Map;

public record ShowOptionsEvent(FontType type, Map<String, Integer> options) implements Event {
}

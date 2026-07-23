package oxy.base.api.event.dialogue;

import oxy.base.api.event.api.Event;
import oxy.base.api.render.elements.text.font.FontType;

import java.util.Map;

public record ShowOptionsEvent(FontType type, Map<String, Integer> options) implements Event {
    public ShowOptionsEvent(Map<String, Integer> options) {
        this(FontType.NotoSans, options);
    }
}

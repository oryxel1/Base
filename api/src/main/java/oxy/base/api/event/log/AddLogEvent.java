package oxy.base.api.event.log;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import oxy.base.api.event.api.Event;
import oxy.base.api.event.dialogue.enums.TextOffset;
import oxy.base.api.render.elements.Dialogue;

@Builder(toBuilder = true, builderClassName = "Builder")
public record AddLogEvent(Type type, int index, TextOffset offset, Dialogue... dialogues) implements Event {
    @RequiredArgsConstructor
    public enum Type {
        CLEAR("Clear"), NEW_LINE("New Line");

        private final String name;

        public static String[] getAlls() {
            String[] strings = new String[values().length];
            for (int i = 0; i < values().length; i++) {
                final String name = values()[i].name;
                strings[i] = name;
            }
            return strings;
        }
    }
}

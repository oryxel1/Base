package oxy.base.api.event;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import oxy.base.api.effects.ScreenEffect;
import oxy.base.api.event.api.Event;

@Builder(toBuilder = true, builderClassName = "Builder")
public record ScreenEffectEvent(Type type, ScreenEffect effect) implements Event {
    @RequiredArgsConstructor
    public enum Type {
        REMOVE("Remove"), ADD("Add"), CLEAR_ALL("Clear All");

        private final String name;

        public static String[] getAlls() {
            String[] strings = new String[values().length];
            for (int i = 0; i < values().length; i++) {
                strings[i] = values()[i].name;
            }
            return strings;
        }
    }
}

package oxy.base.api.event;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import oxy.base.api.event.api.Event;
import oxy.base.api.utils.FileInfo;

@Builder(toBuilder = true, builderClassName = "Builder")
public record PopupEvent(Type type, FileInfo popup) implements Event {
    @RequiredArgsConstructor
    public enum Type {
        CLEAR("Clear"), SET("Set");

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

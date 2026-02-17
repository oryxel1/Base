package oxy.bascenario.api.event.dialogue.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OffsetType {
    Center("Center"),
    Left("Left"),
    Right("Right");

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

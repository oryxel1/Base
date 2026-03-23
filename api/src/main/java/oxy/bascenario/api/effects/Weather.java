package oxy.bascenario.api.effects;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Weather {
    CLEAR("Clear"), RAIN("Rain"), SNOW("Snow");

    private final String name;

    public static String[] getAlls() {
        String[] strings = new String[values().length];
        for (int i = 0; i < values().length; i++) {
            strings[i] = values()[i].name;
        }
        return strings;
    }
}

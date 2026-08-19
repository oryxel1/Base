package oxy.base.api.effects;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum EaseType {
    EASE_IN("Ease In"), EASE_OUT("Ease Out"), EASE_IN_OUT("Ease In Out");

    private final String name;

    public static String[] getAlls() {
        String[] strings = new String[values().length];
        for (int i = 0; i < values().length; i++) {
            strings[i] = values()[i].name;
        }
        return strings;
    }
}

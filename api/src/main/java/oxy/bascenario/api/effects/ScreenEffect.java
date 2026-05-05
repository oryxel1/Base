package oxy.bascenario.api.effects;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ScreenEffect {
    GRAY_FILTER("Gray Filter"),
    SMOKE("Smoke"),
    SHINING("Shining"),
    SHINING_NO_BG("Shining No Background");

    private final String name;

    public static String[] getAlls() {
        String[] strings = new String[values().length];
        for (int i = 0; i < values().length; i++) {
            strings[i] = values()[i].name;
        }
        return strings;
    }
}

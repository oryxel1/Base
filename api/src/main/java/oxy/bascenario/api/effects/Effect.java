package oxy.bascenario.api.effects;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Effect {
    RAINBOW("Rainbow"), HOLOGRAM("Hologram"), BLUR("Blur"), OUTLINE("Outline");
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

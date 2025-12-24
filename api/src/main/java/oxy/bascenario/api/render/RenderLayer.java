package oxy.bascenario.api.render;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum RenderLayer {
    TOP("Top"), BEHIND_DIALOGUE("Behind dialogue"), ABOVE_DIALOGUE("Above dialogue");

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

package oxy.bascenario.api.render.elements.text.font;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum FontStyle {
    REGULAR("Regular"), SEMI_BOLD("Semi Bold"), BOLD("Bold");

    private final String name;

    public static String[] getAlls() {
        String[] strings = new String[FontStyle.values().length];
        for (int i = 0; i < FontStyle.values().length; i++) {
            final String name = FontStyle.values()[i].name;
            strings[i] = name;
        }
        return strings;
    }
}
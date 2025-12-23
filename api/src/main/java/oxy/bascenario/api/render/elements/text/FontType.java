package oxy.bascenario.api.render.elements.text;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum FontType {
    REGULAR("Regular"), SEMI_BOLD("Semi Bold"), BOLD("Bold");

    private final String name;

    public static String[] getAlls() {
        String[] strings = new String[FontType.values().length];
        for (int i = 0; i < FontType.values().length; i++) {
            final String name = FontType.values()[i].name;
            strings[i] = name;
        }
        return strings;
    }

    public static String toName(FontType type) {
        return switch (type) {
            case SEMI_BOLD -> "NotoSansSemiBold";
            case BOLD -> "NotoSansBold";
            default -> "NotoSansRegular";
        };
    }
}
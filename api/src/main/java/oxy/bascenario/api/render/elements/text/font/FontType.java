package oxy.bascenario.api.render.elements.text.font;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum FontType {
    NotoSans("Noto Sans (EN)", "NotoSans"), Gyeonggi("Gyeonggi (KR)", "Gyeonggi"), ShinMaruGo("Shin Maru Go (JP/CN", "ShinMaruGo");

    private final String name, asset;

    public static String[] getAlls() {
        String[] strings = new String[values().length];
        for (int i = 0; i < values().length; i++) {
            final String name = values()[i].name;
            strings[i] = name;
        }
        return strings;
    }

    public String toName(FontStyle style) {
        return switch (style) {
            case SEMI_BOLD -> this.asset + "SemiBold";
            case BOLD -> this.asset + "Bold";
            default -> this.asset + "Regular";
        };
    }
}

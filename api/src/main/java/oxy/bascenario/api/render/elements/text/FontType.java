package oxy.bascenario.api.render.elements.text;

public enum FontType {
    REGULAR, SEMI_BOLD, BOLD;

    public static String toName(FontType type) {
        return switch (type) {
            case SEMI_BOLD -> "NotoSansSemiBold";
            case BOLD -> "NotoSansBold";
            default -> "NotoSansRegular";
        };
    }
}
package oxy.bascenario.api.elements;

import lombok.Builder;

// Yep there are no name, associations, etc... I want this to be a separate thing.
// So for example, a new dialogue line could be added after 600ms for eg, you will see what I mean.
@Builder(toBuilder = true)
public class Dialogue {
    private int index;
    @Builder.Default
    private String dialogue = "";
    @Builder.Default
    private float playSpeed = 1;
    @Builder.Default
    private FontType fontType = FontType.REGULAR;

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
}

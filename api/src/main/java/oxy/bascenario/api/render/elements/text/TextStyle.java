package oxy.bascenario.api.render.elements.text;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TextStyle {
    SHADOW(1), BOLD(1 << 1), ITALIC(1 << 2), UNDERLINE(1 << 3), STRIKETHROUGH(1 << 4);

    private final int value;
}

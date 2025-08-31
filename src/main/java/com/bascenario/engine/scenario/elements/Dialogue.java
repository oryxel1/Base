package com.bascenario.engine.scenario.elements;

import lombok.Builder;

@Builder
public record Dialogue(long index, String dialogue, String name, String association,
                       double playSpeed, float textScale, FontType fontType, boolean cutscene, boolean closeOnClick) {
    public static long MS_PER_WORD = 38L;

    public enum FontType {
        REGULAR, BOLD
    }
}
package com.bascenario.engine.scenario.elements;

import lombok.Builder;

@Builder
public record Dialogue(long index, String dialogue, String name, String association,
                       double playSpeed, float textScale, FontType fontType, boolean cutscene, boolean closeOnClick) {
    public static long MS_PER_WORD = 38L;

//    public long getMaxDuration() {
//        long msPerWord = (long) (Dialogue.MS_PER_WORD * (1 / playSpeed()));
//        return msPerWord * 10 + (msPerWord * (dialogue.length() - 1)) + (msPerWord * 10);
//    }

    public enum FontType {
        REGULAR, BOLD
    }
}
package com.bascenario.engine.scenario.elements;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;

@Builder
public record Dialogue(@SerializedName("dialogue-index") long index, String dialogue, String name, String association,
                       @SerializedName("play-speed") double playSpeed,
                       @SerializedName("text-scale") float textScale,
                       @SerializedName("font-type") FontType fontType, boolean cutscene,
                       @SerializedName("close-dialogue-on-click") boolean closeOnClick) {
    public static long MS_PER_WORD = 38L;

    public enum FontType {
        REGULAR, BOLD
    }
}
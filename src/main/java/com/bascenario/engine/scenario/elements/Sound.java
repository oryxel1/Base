package com.bascenario.engine.scenario.elements;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;

@Builder
public record Sound(String path, @SerializedName("max-volume") float maxVolume, @SerializedName("fade-in") long fadeIn) {
}
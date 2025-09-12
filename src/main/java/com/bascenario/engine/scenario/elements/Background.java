package com.bascenario.engine.scenario.elements;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;

@Builder
public record Background(String path, @SerializedName("fade-in") boolean fadeIn, @SerializedName("fade-out") boolean fadeOut) {
}
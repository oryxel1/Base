package com.bascenario.engine.scenario.elements;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;

@Builder
public record Sprite(String skeleton, String atlas, @SerializedName("default-animation") String defaultAnimation, @SerializedName("fade-in") boolean fadeIn) {
}
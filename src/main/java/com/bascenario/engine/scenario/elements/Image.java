package com.bascenario.engine.scenario.elements;

import com.google.gson.annotations.SerializedName;

public record Image(String path, float width, float height, @SerializedName("fade-in") boolean fadeIn, @SerializedName("fade-out") boolean fadeOut) {
}
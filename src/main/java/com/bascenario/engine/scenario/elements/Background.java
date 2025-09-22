package com.bascenario.engine.scenario.elements;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Builder
public final class Background {
    private String path;
    @SerializedName("fade-in")
    private boolean fadeIn;
    @SerializedName("fade-out")
    private boolean fadeOut;

    public Background(String path, boolean fadeIn, boolean fadeOut) {
        this.path = path;
        this.fadeIn = fadeIn;
        this.fadeOut = fadeOut;
    }

    public String path() {
        return path;
    }

    public void path(String path) {
        this.path = path;
    }

    public boolean fadeIn() {
        return fadeIn;
    }

    public void fadeIn(boolean fadeIn) {
        this.fadeIn = fadeIn;
    }

    public boolean fadeOut() {
        return fadeOut;
    }

    public void fadeOut(boolean fadeOut) {
        this.fadeOut = fadeOut;
    }
}
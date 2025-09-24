package com.bascenario.engine.scenario.elements;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public final class PopupImage {
    private String path;
    private long duration;

    public PopupImage(String path, long duration) {
        this.path = path;
        this.duration = duration;
    }

    public String path() {
        return path;
    }

    public void path(String path) {
        this.path = path;
    }

    public long duration() {
        return duration;
    }

    public void duration(long duration) {
        this.duration = duration;
    }
}
package com.bascenario.engine.scenario.event.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Event {
    @Getter
    private final long duration;

    public void onStart() {}
    public void onEnd() {}
    public void render(long time, int width, int height) {}
}

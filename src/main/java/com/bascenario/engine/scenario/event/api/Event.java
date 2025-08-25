package com.bascenario.engine.scenario.event.api;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Event {
    private final long duration;

    public void onStart() {}
    public void onEnd() {}
    public void render(int width, int height) {}
}

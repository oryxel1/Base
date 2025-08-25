package com.bascenario.engine.scenario.event.render;

import com.bascenario.engine.scenario.event.api.Event;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EventRenderer {
    private final Event event;
    private final long startTime = System.currentTimeMillis();

    public long getTime() {
        return System.currentTimeMillis() - this.startTime;
    }

    public void onStart() {
        this.event.onStart();
    }

    public void onEnd() {
        this.event.onEnd();
    }

    public void render(long time, int width, int height) {
        this.event.render(time, width, height);
    }

    public boolean isFinished() {
        return System.currentTimeMillis() - this.startTime >= this.event.getDuration();
    }
}

package com.bascenario.engine.scenario.event.render;

import com.bascenario.engine.scenario.event.api.Event;
import lombok.RequiredArgsConstructor;
import net.raphimc.thingl.implementation.window.WindowInterface;
import org.joml.Matrix4fStack;

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

    public void render(long time, Matrix4fStack positionMatrix, WindowInterface window) {
        this.event.render(time, positionMatrix, window);
    }

    public boolean isFinished() {
        return System.currentTimeMillis() - this.startTime >= this.event.getDuration();
    }
}

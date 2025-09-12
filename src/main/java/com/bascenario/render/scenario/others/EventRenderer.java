package com.bascenario.render.scenario.others;

import com.bascenario.engine.scenario.event.api.Event;
import com.bascenario.render.scenario.ScenarioScreen;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.raphimc.thingl.implementation.window.WindowInterface;
import org.joml.Matrix4fStack;

@RequiredArgsConstructor
public class EventRenderer {
    @Getter
    private final Event event;
    private final long startTime = System.currentTimeMillis();

    public long getTime() {
        return System.currentTimeMillis() - this.startTime;
    }

    public void onStart(ScenarioScreen screen) {
        this.event.onStart(screen);
    }

    public void onEnd(ScenarioScreen screen) {
        this.event.onEnd(screen);
    }

    public void render(ScenarioScreen screen, long time, Matrix4fStack positionMatrix, WindowInterface window) {
        this.event.render(screen, time, positionMatrix, window);
    }

    public boolean isFinished() {
        return System.currentTimeMillis() - this.startTime >= this.event.getDuration();
    }
}

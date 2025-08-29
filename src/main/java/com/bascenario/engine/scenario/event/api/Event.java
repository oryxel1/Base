package com.bascenario.engine.scenario.event.api;

import com.bascenario.engine.scenario.screen.ScenarioScreen;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.raphimc.thingl.implementation.window.WindowInterface;
import org.joml.Matrix4fStack;

@RequiredArgsConstructor
public class Event {
    @Getter
    protected final long duration;

    public void onStart(ScenarioScreen screen) {}
    public void onEnd(ScenarioScreen screen) {}
    public void render(ScenarioScreen screen, long time, Matrix4fStack positionMatrix, WindowInterface window) {}
}

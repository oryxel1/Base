package com.bascenario.engine.scenario.event.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.raphimc.thingl.implementation.window.WindowInterface;
import org.joml.Matrix4fStack;

@RequiredArgsConstructor
public class Event {
    @Getter
    private final long duration;

    public void onStart() {}
    public void onEnd() {}
    public void render(long time, Matrix4fStack positionMatrix, WindowInterface window) {}
}

package com.bascenario.engine.scenario.event.api;

import com.bascenario.render.scenario.ScenarioScreen;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.raphimc.thingl.implementation.window.WindowInterface;
import org.joml.Matrix4fStack;

@RequiredArgsConstructor
public abstract class Event<T> implements JsonSerializer<T>, JsonDeserializer<T>  {
    @Getter
    protected final long duration;

    public void onStart(ScenarioScreen screen) {}
    public void onEnd(ScenarioScreen screen) {}
    public void render(ScenarioScreen screen, long time, Matrix4fStack positionMatrix, WindowInterface window) {}
}

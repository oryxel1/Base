package com.bascenario.engine.scenario.event.api;

import com.bascenario.render.scenario.ScenarioScreen;
import com.google.gson.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.raphimc.thingl.implementation.window.WindowInterface;
import org.joml.Matrix4fStack;

@RequiredArgsConstructor
public abstract class Event<T>  {
    @Getter
    protected final long duration;

    public void onStart(ScenarioScreen screen) {}
    public void onEnd(ScenarioScreen screen) {}
    public void render(ScenarioScreen screen, long time, Matrix4fStack positionMatrix, WindowInterface window) {}

//    public abstract void renderImGui();

    public abstract void serialize(JsonObject serialized);
    public abstract T deserialize(JsonObject serialized);
    public abstract String type();
}

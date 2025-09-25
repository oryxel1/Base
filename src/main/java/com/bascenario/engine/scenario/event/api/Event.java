package com.bascenario.engine.scenario.event.api;

import com.bascenario.render.scenario.ScenarioScreen;
import com.google.gson.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.raphimc.thingl.implementation.window.WindowInterface;
import org.joml.Matrix4fStack;

@AllArgsConstructor
public abstract class Event<T>  {
    @Getter @Setter
    protected long duration;

    public void onStart(ScenarioScreen screen) {}
    public void onEnd(ScenarioScreen screen) {}
    public void render(ScenarioScreen screen, long time, Matrix4fStack positionMatrix, WindowInterface window) {}

    public abstract void renderImGui();
    public abstract T defaultEvent();

    public abstract void serialize(JsonObject serialized);
    public abstract T deserialize(JsonObject serialized);
    public abstract String type();
}

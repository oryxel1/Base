package oxy.bascenario.engine.scenario.event.base;

import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
public abstract class Event<T>  {
    @Getter
    @Setter
    protected long duration;

//    public void onStart(ScenarioScreen screen) {}
//    public void onEnd(ScenarioScreen screen) {}
//    public void render(ScenarioScreen screen, long time, Matrix4fStack positionMatrix, WindowInterface window) {}

    public abstract void configurationRender();
    public abstract T defaultEvent();

    public abstract List<String> downloads();

    public abstract void serialize(JsonObject serialized);
    public abstract T deserialize(JsonObject serialized);
    public abstract String type();
}
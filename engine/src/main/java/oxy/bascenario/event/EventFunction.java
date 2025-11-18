package oxy.bascenario.event;

import com.google.gson.JsonObject;

public interface EventFunction<T> {
    default void start() {}
    default void end() {}
    default void render() {}

    default void configurationRender() {
    }

    void serialize(JsonObject serialized);
    T deserialize(JsonObject serialized);
}

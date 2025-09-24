package com.bascenario.engine.scenario.event.impl;

import com.bascenario.engine.scenario.event.api.Event;
import com.bascenario.util.GsonUtil;
import com.google.gson.*;
import imgui.ImGui;
import lombok.Getter;

// This event will get handle separately as a value instead of an actual event.
public class QueueEventEvent extends Event<QueueEventEvent> {
    @Getter
    private final Event<?> queuedEvent;
    public QueueEventEvent(long duration, Event<?> event) {
        super(duration);
        this.queuedEvent = event;
    }

    @Override
    public void renderImGui() {
    }

    @Override
    public QueueEventEvent defaultEvent() {
        return null;

    }
    @Override
    public void serialize(JsonObject serialized) {
        serialized.addProperty("duration", this.duration);
        serialized.add("queued-event", GsonUtil.toJson(this.queuedEvent));
    }

    @Override
    public QueueEventEvent deserialize(JsonObject serialized) {
        return new QueueEventEvent(serialized.get("duration").getAsLong(), GsonUtil.getGson().fromJson(serialized.get("queued-event"), Event.class));
    }

    @Override
    public String type() {
        return "queue-event";
    }
}

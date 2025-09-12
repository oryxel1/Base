package com.bascenario.engine.scenario.event.impl;

import com.bascenario.engine.scenario.event.api.Event;
import com.bascenario.util.GsonUtil;
import com.google.gson.*;
import lombok.Getter;

import java.lang.reflect.Type;

public class QueueEventEvent extends Event<QueueEventEvent> {
    @Getter
    private final Event<?> queuedEvent;
    public QueueEventEvent(long duration, Event<?> event) {
        super(duration);
        this.queuedEvent = event;
    }

    @Override
    public QueueEventEvent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final JsonObject serialized = json.getAsJsonObject();
        return new QueueEventEvent(serialized.get("duration").getAsLong(), GsonUtil.getGson().fromJson(serialized.get("dialogue").getAsString().trim(), Event.class));
    }

    @Override
    public JsonElement serialize(QueueEventEvent src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject serialized = new JsonObject();
        serialized.addProperty("duration", src.duration);
        serialized.add("queued-event", GsonUtil.toJson(src.queuedEvent));
        return serialized;
    }
}

package com.bascenario.engine.scenario.event.impl.sprite;

import com.bascenario.engine.scenario.event.api.Event;
import com.bascenario.render.scenario.others.SpriteRender;
import com.bascenario.render.scenario.ScenarioScreen;
import com.google.gson.*;

import java.lang.reflect.Type;

public class SpriteLocationEvent extends Event<SpriteLocationEvent> {
    private final int spriteId;
    private final float x, y;
    public SpriteLocationEvent(int spriteId, long duration, float x, float y) {
        super(duration);
        this.spriteId = spriteId;
        this.x = x;
        this.y = y;
    }

    @Override
    public void onStart(ScenarioScreen screen) {
        SpriteRender render = null;
        for (SpriteRender spriteRender : screen.getSprites()) {
            if (spriteRender.getSpriteId() == this.spriteId) {
                render = spriteRender;
            }
        }

        if (render == null) {
            return;
        }

        render.lerpTo(this.x, this.y, duration);
    }

    @Override
    public SpriteLocationEvent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final JsonObject serialized = json.getAsJsonObject();
        return new SpriteLocationEvent(
                serialized.get("sprite").getAsInt(),
                serialized.get("duration").getAsLong(),
                serialized.get("x").getAsFloat(),
                serialized.get("y").getAsFloat()
        );
    }

    @Override
    public JsonElement serialize(SpriteLocationEvent src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject serialized = new JsonObject();
        serialized.addProperty("sprite", this.spriteId);
        serialized.addProperty("duration", this.duration);
        serialized.addProperty("x", this.x);
        serialized.addProperty("y", this.y);
        return serialized;
    }
}

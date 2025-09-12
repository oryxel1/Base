package com.bascenario.engine.scenario.event.impl.sprite;

import com.bascenario.engine.scenario.event.api.Event;
import com.bascenario.render.scenario.others.SpriteRender;
import com.bascenario.render.scenario.ScenarioScreen;
import com.google.gson.*;

import java.lang.reflect.Type;

public class SpriteScaleEvent extends Event<SpriteScaleEvent> {
    private final int spriteId;
    private final float scale;
    public SpriteScaleEvent(int spriteId, long duration, float scale) {
        super(duration);
        this.spriteId = spriteId;
        this.scale = scale;
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

        render.lerpTo(this.scale, this.duration);
    }

    @Override
    public SpriteScaleEvent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final JsonObject serialized = json.getAsJsonObject();
        return new SpriteScaleEvent(
                serialized.get("sprite").getAsInt(),
                serialized.get("duration").getAsLong(),
                serialized.get("scale").getAsFloat()
        );
    }

    @Override
    public JsonElement serialize(SpriteScaleEvent src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject serialized = new JsonObject();
        serialized.addProperty("sprite", this.spriteId);
        serialized.addProperty("duration", this.duration);
        serialized.addProperty("scale", this.scale);
        return serialized;
    }
}

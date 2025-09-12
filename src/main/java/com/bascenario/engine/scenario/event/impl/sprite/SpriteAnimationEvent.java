package com.bascenario.engine.scenario.event.impl.sprite;

import com.bascenario.engine.scenario.event.api.Event;
import com.bascenario.render.scenario.others.SpriteRender;
import com.bascenario.render.scenario.ScenarioScreen;
import com.google.gson.*;

import java.lang.reflect.Type;

public class SpriteAnimationEvent extends Event<SpriteAnimationEvent> {
    private final int spriteId;
    private final String animation;
    private final int layer;
    private final boolean loop;

    public SpriteAnimationEvent(int spriteId, String animation, int layer, boolean loop) {
        super(0);
        this.spriteId = spriteId;
        this.animation = animation;
        this.layer = layer;
        this.loop = loop;
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

        render.playAnimation(layer, animation, loop);
    }

    @Override
    public JsonElement serialize(SpriteAnimationEvent src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject serialized = new JsonObject();
        serialized.addProperty("sprite", this.spriteId);
        serialized.addProperty("animation", this.animation);
        serialized.addProperty("layer", this.layer);
        serialized.addProperty("loop", this.loop);
        return serialized;
    }

    @Override
    public SpriteAnimationEvent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final JsonObject serialized = json.getAsJsonObject();
        return new SpriteAnimationEvent(
                serialized.get("sprite").getAsInt(),
                serialized.get("animation").getAsString(),
                serialized.get("layer").getAsInt(),
                serialized.get("loop").getAsBoolean()
        );
    }
}

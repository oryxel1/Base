package com.bascenario.engine.scenario.event.impl.sprite;

import com.bascenario.engine.scenario.event.api.Event;
import com.bascenario.render.scenario.others.SpriteRender;
import com.bascenario.render.scenario.ScenarioScreen;
import com.bascenario.util.render.ImGuiUtil;
import com.google.gson.*;

public class SpriteFadeEvent extends Event<SpriteFadeEvent> {
    private int spriteId;
    private float value;
    public SpriteFadeEvent(int spriteId, long duration, float value) {
        super(duration);
        this.spriteId = spriteId;
        this.value = value;
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

        render.setFadeColor(this.value, this.duration);
    }

    @Override
    public void renderImGui() {
        this.spriteId = ImGuiUtil.inputInt("Sprite ID", this.spriteId);
        this.duration = ImGuiUtil.sliderInt("Fade Duration", (int) this.duration, 0, 10000);
        this.value = ImGuiUtil.sliderFloat("Value", this.value, 0, 1);
    }

    @Override
    public SpriteFadeEvent defaultEvent() {
        return new SpriteFadeEvent(0, 1000L, 0.5F);
    }

    @Override
    public void serialize(JsonObject serialized) {
        serialized.addProperty("sprite", this.spriteId);
        serialized.addProperty("duration", this.duration);
        serialized.addProperty("value", this.value);
    }

    @Override
    public SpriteFadeEvent deserialize(JsonObject serialized) {
        return new SpriteFadeEvent(
                serialized.get("sprite").getAsInt(),
                serialized.get("duration").getAsLong(),
                serialized.get("value").getAsFloat()
        );
    }

    @Override
    public String type() {
        return "sprite-fade";
    }
}

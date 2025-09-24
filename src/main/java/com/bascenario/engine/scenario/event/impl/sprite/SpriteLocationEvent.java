package com.bascenario.engine.scenario.event.impl.sprite;

import com.bascenario.engine.scenario.event.api.Event;
import com.bascenario.render.scenario.others.SpriteRender;
import com.bascenario.render.scenario.ScenarioScreen;
import com.bascenario.util.render.ImGuiUtil;
import com.google.gson.*;

public class SpriteLocationEvent extends Event<SpriteLocationEvent> {
    private int spriteId;
    private float x, y;
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
    public void renderImGui() {
        this.spriteId = ImGuiUtil.inputInt("Sprite ID", this.spriteId);
        this.duration = ImGuiUtil.sliderInt("Move Duration", (int) this.duration, 0, 10000);
        this.x = ImGuiUtil.sliderInt("X (%)", (int) this.x, -800, 800);
        this.y = ImGuiUtil.sliderInt("Y (%)", (int) this.y, -800, 800);
    }

    @Override
    public SpriteLocationEvent defaultEvent() {
        return new SpriteLocationEvent(0, 0, 0, 50);
    }

    @Override
    public void serialize(JsonObject serialized) {
        serialized.addProperty("sprite", this.spriteId);
        serialized.addProperty("duration", this.duration);
        serialized.addProperty("x", this.x);
        serialized.addProperty("y", this.y);
    }

    @Override
    public SpriteLocationEvent deserialize(JsonObject serialized) {
        return new SpriteLocationEvent(
                serialized.get("sprite").getAsInt(),
                serialized.get("duration").getAsLong(),
                serialized.get("x").getAsFloat(),
                serialized.get("y").getAsFloat()
        );
    }

    @Override
    public String type() {
        return "set-sprite-location";
    }
}

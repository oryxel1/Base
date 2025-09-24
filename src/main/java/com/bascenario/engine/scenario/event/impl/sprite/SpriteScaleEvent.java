package com.bascenario.engine.scenario.event.impl.sprite;

import com.bascenario.engine.scenario.event.api.Event;
import com.bascenario.render.scenario.others.SpriteRender;
import com.bascenario.render.scenario.ScenarioScreen;
import com.bascenario.util.render.ImGuiUtil;
import com.google.gson.*;

import java.lang.reflect.Type;

public class SpriteScaleEvent extends Event<SpriteScaleEvent> {
    private int spriteId;
    private float scale;
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
    public void renderImGui() {
        this.spriteId = ImGuiUtil.inputInt("Sprite ID", this.spriteId);
        this.duration = ImGuiUtil.sliderInt("Fade Duration", (int) this.duration, 0, 10000);
        this.scale = ImGuiUtil.sliderFloat("Scale", this.scale, 0, 10);
    }

    @Override
    public SpriteScaleEvent defaultEvent() {
        return new SpriteScaleEvent(0, 1000, 1);
    }

    @Override
    public void serialize(JsonObject serialized) {
        serialized.addProperty("sprite", this.spriteId);
        serialized.addProperty("duration", this.duration);
        serialized.addProperty("scale", this.scale);
    }

    @Override
    public SpriteScaleEvent deserialize(JsonObject serialized) {
        return new SpriteScaleEvent(
                serialized.get("sprite").getAsInt(),
                serialized.get("duration").getAsLong(),
                serialized.get("scale").getAsFloat()
        );
    }

    @Override
    public String type() {
        return "scale-sprite";
    }
}

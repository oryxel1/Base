package com.bascenario.engine.scenario.event.impl.sprite;

import com.bascenario.engine.scenario.event.api.Event;
import com.bascenario.render.scenario.others.SpriteRender;
import com.bascenario.render.scenario.ScenarioScreen;
import com.bascenario.util.render.ImGuiUtil;
import com.google.gson.*;

public class SpriteAnimationEvent extends Event<SpriteAnimationEvent> {
    private int spriteId;
    private String animation;
    private int layer;
    private boolean loop;

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
    public void renderImGui() {
        this.spriteId = ImGuiUtil.inputInt("Sprite ID", this.spriteId);
        this.animation = ImGuiUtil.inputText("Animation", this.animation);
        this.layer = ImGuiUtil.inputInt("Animation Layer", this.layer);
        this.loop = ImGuiUtil.checkbox("Loop", this.loop);
    }

    @Override
    public SpriteAnimationEvent defaultEvent() {
        return new SpriteAnimationEvent(0, "Idle_01", 1, true);
    }

    @Override
    public void serialize(JsonObject serialized) {
        serialized.addProperty("sprite", this.spriteId);
        serialized.addProperty("animation", this.animation);
        serialized.addProperty("layer", this.layer);
        serialized.addProperty("loop", this.loop);
    }

    @Override
    public SpriteAnimationEvent deserialize(JsonObject serialized) {
        return new SpriteAnimationEvent(
                serialized.get("sprite").getAsInt(),
                serialized.get("animation").getAsString(),
                serialized.get("layer").getAsInt(),
                serialized.get("loop").getAsBoolean()
        );
    }

    @Override
    public String type() {
        return "play-sprite-animation";
    }
}

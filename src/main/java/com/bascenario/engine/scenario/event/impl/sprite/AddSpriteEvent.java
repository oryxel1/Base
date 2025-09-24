package com.bascenario.engine.scenario.event.impl.sprite;

import com.bascenario.engine.scenario.elements.Sprite;
import com.bascenario.engine.scenario.event.api.Event;
import com.bascenario.render.scenario.others.SpriteRender;
import com.bascenario.render.scenario.ScenarioScreen;
import com.bascenario.util.GsonUtil;
import com.google.gson.*;
import lombok.Getter;

import java.lang.reflect.Type;

public class AddSpriteEvent extends Event<AddSpriteEvent> {
    private final Sprite sprite;
    private final int spriteId;
    
    public AddSpriteEvent(Sprite sprite, int spriteId) {
        super(0);
        this.sprite = sprite;
        this.spriteId = spriteId;
    }

    @Override
    public void onStart(ScenarioScreen screen) {
        final SpriteRender spriteRender = new SpriteRender(this.spriteId, this.sprite);
        spriteRender.init();
        screen.getSprites().add(spriteRender);
    }

    @Override
    public void renderImGui() {

    }

    @Override
    public AddSpriteEvent defaultEvent() {
        return new AddSpriteEvent(new Sprite("", "", "", true), 0);
    }

    @Override
    public void serialize(JsonObject serialized) {
        serialized.add("sprite", GsonUtil.toJson(this.sprite));
        serialized.addProperty("sprite-id", this.spriteId);
    }

    @Override
    public AddSpriteEvent deserialize(JsonObject serialized) {
        return new AddSpriteEvent(GsonUtil.getGson().fromJson(serialized.get("sprite"), Sprite.class), serialized.get("sprite-id").getAsInt());
    }

    @Override
    public String type() {
        return "add-sprite";
    }
}

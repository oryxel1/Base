package com.bascenario.engine.scenario.event.impl.sprite;

import com.bascenario.engine.scenario.elements.Sprite;
import com.bascenario.engine.scenario.event.api.Event;
import com.bascenario.render.scenario.others.SpriteRender;
import com.bascenario.render.scenario.ScenarioScreen;
import com.bascenario.util.GsonUtil;
import com.google.gson.*;
import net.lenni0451.commons.animation.DynamicAnimation;
import net.lenni0451.commons.animation.easing.EasingFunction;
import net.lenni0451.commons.animation.easing.EasingMode;

import java.lang.reflect.Type;

public class RemoveSpriteEvent extends Event<RemoveSpriteEvent> {
    private final int spriteId;
    private final boolean fadeOut;
    private final long fadeDuration;

    public RemoveSpriteEvent(int spriteId, boolean fadeOut, long fadeDuration) {
        super(fadeDuration > 0 ? fadeDuration + 200L : 0);
        this.spriteId = spriteId;
        this.fadeOut = fadeOut;
        this.fadeDuration = fadeDuration;
    }

    public RemoveSpriteEvent(int spriteId) {
        super(0);
        this.spriteId = spriteId;
        this.fadeOut = false;
        this.fadeDuration = 0;
    }

    @Override
    public void onStart(ScenarioScreen screen) {
        if (!this.fadeOut) {
            this.yeetSprite(screen);
        } else {
            final SpriteRender render = getRender(screen);
            if (render != null && render.getFadeColor() != null) {
                render.setFadeColor(new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_IN_OUT, this.fadeDuration, render.getFadeColor().getValue()));
                render.getFadeColor().setTarget(1);
            }
        }
    }

    @Override
    public void onEnd(ScenarioScreen screen) {
        if (this.fadeOut) {
            this.yeetSprite(screen);
        }
    }

    private SpriteRender getRender(final ScenarioScreen screen) {
        SpriteRender render = null;
        for (SpriteRender spriteRender : screen.getSprites()) {
            if (spriteRender.getSpriteId() == this.spriteId) {
                render = spriteRender;
            }
        }

        return render;
    }

    private void yeetSprite(final ScenarioScreen screen) {
        SpriteRender render = getRender(screen);
        render.dispose();
        screen.getSprites().remove(render);
    }

    @Override
    public RemoveSpriteEvent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final JsonObject serialized = json.getAsJsonObject();
        return new RemoveSpriteEvent(
                serialized.get("sprite").getAsInt(),
                serialized.get("fade-out").getAsBoolean(),
                serialized.get("fade-duration").getAsLong()
        );
    }

    @Override
    public JsonElement serialize(RemoveSpriteEvent src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject serialized = new JsonObject();
        serialized.addProperty("sprite", src.spriteId);
        serialized.addProperty("fade-out", src.fadeOut);
        serialized.addProperty("fade-duration", src.fadeDuration);
        return serialized;
    }
}

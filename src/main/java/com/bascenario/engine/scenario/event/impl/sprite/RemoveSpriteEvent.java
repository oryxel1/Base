package com.bascenario.engine.scenario.event.impl.sprite;

import com.bascenario.engine.scenario.event.api.Event;
import com.bascenario.render.scenario.others.SpriteRender;
import com.bascenario.render.scenario.ScenarioScreen;
import com.google.gson.*;
import net.lenni0451.commons.animation.DynamicAnimation;
import net.lenni0451.commons.animation.easing.EasingFunction;
import net.lenni0451.commons.animation.easing.EasingMode;

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
    public void serialize(JsonObject serialized) {
        serialized.addProperty("sprite", this.spriteId);
        serialized.addProperty("fade-out", this.fadeOut);
        serialized.addProperty("fade-duration", this.fadeDuration);
    }

    @Override
    public RemoveSpriteEvent deserialize(JsonObject serialized) {
        return new RemoveSpriteEvent(
                serialized.get("sprite").getAsInt(),
                serialized.get("fade-out").getAsBoolean(),
                serialized.get("fade-duration").getAsLong()
        );
    }

    @Override
    public String type() {
        return "remove-sprite";
    }
}

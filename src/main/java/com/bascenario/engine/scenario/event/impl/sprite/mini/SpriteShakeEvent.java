package com.bascenario.engine.scenario.event.impl.sprite.mini;

import com.bascenario.engine.scenario.event.api.Event;
import com.bascenario.render.scenario.others.SpriteRender;
import com.bascenario.render.scenario.ScenarioScreen;
import com.google.gson.*;
import net.raphimc.thingl.implementation.window.WindowInterface;
import org.joml.Matrix4fStack;

public class SpriteShakeEvent extends Event<SpriteShakeEvent> {
    private final long shakeDuration;
    private final int spriteId;
    public SpriteShakeEvent(int spriteId, long duration, long shakeDuration) {
        super(duration);
        this.spriteId = spriteId;
        this.shakeDuration = shakeDuration;
    }

    private boolean startShaking;
    private float cachedXPos;
    private long last = -1;
    private int phase;

    @Override
    public void render(ScenarioScreen screen, long time, Matrix4fStack positionMatrix, WindowInterface window) {
        SpriteRender render = null;
        for (SpriteRender spriteRender : screen.getSprites()) {
            if (spriteRender.getSpriteId() == this.spriteId) {
                render = spriteRender;
            }
        }

        if (render == null) {
            return;
        }

        if (!this.startShaking) {
            this.cachedXPos = render.getXLocation().getValue();
            this.startShaking = true;

            render.lerpTo(this.cachedXPos + 1, render.getYLocation().getValue(), this.shakeDuration);
            this.last = System.currentTimeMillis();
        }

        if (this.last != -1 && System.currentTimeMillis() - this.last > this.shakeDuration) {
            render.lerpTo(this.cachedXPos + (phase % 2 == 0 ? 1 : -1), render.getYLocation().getValue(), this.shakeDuration);
            phase++;
            this.last = System.currentTimeMillis();
        }
    }

    @Override
    public void onEnd(ScenarioScreen screen) {
        SpriteRender render = null;
        for (SpriteRender spriteRender : screen.getSprites()) {
            if (spriteRender.getSpriteId() == this.spriteId) {
                render = spriteRender;
            }
        }

        if (render == null) {
            return;
        }

        render.lerpTo(this.cachedXPos, render.getYLocation().getValue(), this.shakeDuration);
    }

    @Override
    public void serialize(JsonObject serialized) {
        serialized.addProperty("sprite", this.spriteId);
        serialized.addProperty("duration", this.duration);
        serialized.addProperty("shake-duration", this.shakeDuration);
    }

    @Override
    public SpriteShakeEvent deserialize(JsonObject serialized) {
        return new SpriteShakeEvent(
                serialized.get("sprite").getAsInt(),
                serialized.get("duration").getAsLong(),
                serialized.get("shake-duration").getAsLong()
        );
    }

    @Override
    public String type() {
        return "sprite-shake";
    }
}

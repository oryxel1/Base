package com.bascenario.engine.scenario.event.impl.sprite.mini;

import com.bascenario.engine.scenario.elements.Sprite;
import com.bascenario.engine.scenario.event.api.Event;
import com.bascenario.engine.scenario.render.SpriteRender;
import com.bascenario.engine.scenario.screen.ScenarioScreen;
import net.raphimc.thingl.implementation.window.WindowInterface;
import org.joml.Matrix4fStack;

public class SpriteShakeEvent extends Event {
    private final long shakeDuration;
    private final Sprite sprite;
    public SpriteShakeEvent(long duration, long shakeDuration, Sprite sprite) {
        super(duration);
        this.shakeDuration = shakeDuration;
        this.sprite = sprite;
    }

    private boolean startShaking;
    private float cachedXPos;
    private long last = -1;
    private int phase;

    @Override
    public void render(ScenarioScreen screen, long time, Matrix4fStack positionMatrix, WindowInterface window) {
        SpriteRender render = null;
        for (SpriteRender spriteRender : screen.getSprites()) {
            if (spriteRender.getSprite().equals(this.sprite)) {
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
            if (spriteRender.getSprite().equals(this.sprite)) {
                render = spriteRender;
            }
        }

        if (render == null) {
            return;
        }

        render.lerpTo(this.cachedXPos, render.getYLocation().getValue(), this.shakeDuration);
    }
}

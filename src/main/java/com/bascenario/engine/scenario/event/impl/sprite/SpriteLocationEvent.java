package com.bascenario.engine.scenario.event.impl.sprite;

import com.bascenario.engine.scenario.Scenario;
import com.bascenario.engine.scenario.elements.Sprite;
import com.bascenario.engine.scenario.event.api.Event;
import com.bascenario.engine.scenario.render.SpriteRender;
import com.bascenario.engine.scenario.screen.ScenarioScreen;

public class SpriteLocationEvent extends Event {
    private final Sprite sprite;
    private final float x, y;
    public SpriteLocationEvent(long duration, Sprite sprite, int x, int y) {
        super(duration);
        this.sprite = sprite;
        this.x = x;
        this.y = y;
    }

    @Override
    public void onStart(ScenarioScreen screen) {
        SpriteRender render = null;
        for (SpriteRender spriteRender : screen.getSprites()) {
            if (spriteRender.getSprite().equals(this.sprite)) {
                render = spriteRender;
            }
        }

        if (render == null) {
            return;
        }

        render.lerpTo(this.x, this.y, duration);
    }
}

package com.bascenario.engine.scenario.event.impl.sprite;

import com.bascenario.engine.scenario.elements.Sprite;
import com.bascenario.engine.scenario.event.api.Event;
import com.bascenario.engine.scenario.render.SpriteRender;
import com.bascenario.engine.scenario.screen.ScenarioScreen;

public class SpriteFadeEvent extends Event {
    private final Sprite sprite;
    private final float value;
    public SpriteFadeEvent(long duration, Sprite sprite, float value) {
        super(duration);
        this.sprite = sprite;
        this.value = value;
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

        render.setFadeColor(this.value, this.duration);
    }
}

package com.bascenario.engine.scenario.event.impl.sprite;

import com.bascenario.engine.scenario.Scenario;
import com.bascenario.engine.scenario.elements.Sprite;
import com.bascenario.engine.scenario.event.api.Event;
import com.bascenario.engine.scenario.render.SpriteRender;
import com.bascenario.engine.scenario.screen.ScenarioScreen;

public class SpriteAnimationEvent extends Event {
    private final Sprite sprite;
    private final String animation;
    private final int layer;
    private final boolean loop;

    public SpriteAnimationEvent(Sprite sprite, String animation, int layer, boolean loop) {
        super(0);
        this.sprite = sprite;
        this.animation = animation;
        this.layer = layer;
        this.loop = loop;
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

        render.playAnimation(layer, animation, loop);
    }
}

package com.bascenario.engine.scenario.event.impl.sprite;

import com.bascenario.engine.scenario.elements.Sprite;
import com.bascenario.engine.scenario.event.api.Event;
import com.bascenario.engine.scenario.render.SpriteRender;
import com.bascenario.engine.scenario.screen.ScenarioScreen;
import lombok.Getter;

public class AddSpriteEvent extends Event {
    @Getter
    private final Sprite sprite;
    public AddSpriteEvent(Sprite sprite) {
        super(0);
        this.sprite = sprite;
    }

    @Override
    public void onStart(ScenarioScreen screen) {
        screen.getSprites().add(new SpriteRender(this.sprite));
    }
}

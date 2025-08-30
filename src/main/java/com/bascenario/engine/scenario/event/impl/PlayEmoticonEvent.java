package com.bascenario.engine.scenario.event.impl;

import com.bascenario.engine.scenario.elements.Sprite;
import com.bascenario.engine.scenario.event.api.Event;
import com.bascenario.engine.scenario.render.EmoticonRender;
import com.bascenario.engine.scenario.render.SpriteRender;
import com.bascenario.engine.scenario.screen.ScenarioScreen;

public class PlayEmoticonEvent extends Event {
    private final Sprite sprite;
    private final Sprite.Emoticon emoticon;
    public PlayEmoticonEvent(Sprite sprite, Sprite.Emoticon emoticon) {
        super(0);
        this.sprite = sprite;
        this.emoticon = emoticon;
    }

    @Override
    public void onStart(ScenarioScreen screen) {
        SpriteRender render = null;
        for (SpriteRender spriteRender : screen.getSprites()) {
            if (spriteRender.getSprite().equals(this.sprite)) {
                render = spriteRender;
            }
        }

        if (render != null) {
            render.getEmoticons().add(new EmoticonRender(this.emoticon));
        }
    }
}

package com.bascenario.engine.scenario.event.impl;

import com.bascenario.audio.AudioManager;
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
        String location = switch (emoticon.type()) {
            case SWEAT -> "assets/base/sounds/SFX_Emoticon_Motion_Sweat.wav";
            case EXCLAMATION_MARK -> "assets/base/sounds/SFX_Emoticon_Motion_Exclaim.wav";
            case ANXIETY -> "assets/base/sounds/SFX_Emoticon_Motion_Upset.wav";
            case THINKING, HESITATED -> "assets/base/sounds/SFX_Emoticon_Motion_Dot.wav";
            case SHY -> "assets/base/sounds/SFX_Emoticon_Motion_Shy.wav";
            case CHAT -> "assets/base/sounds/SFX_Emoticon_Motion_Chat.wav";
            case QUESTION_MARK -> "assets/base/sounds/SFX_Emoticon_Motion_Question.wav";
            case TWINKLE -> "assets/base/sounds/SFX_Emoticon_Motion_Twinkle.wav";
            case SURPRISED -> "assets/base/sounds/SFX_Emoticon_Motion_Surprise.wav";
            case HEART -> "assets/base/sounds/SFX_Emoticon_Motion_Heart.wav";
            case RESPOND -> "assets/base/sounds/SFX_Emoticon_Motion_Respond.wav";
            case ANGRY -> "assets/base/sounds/SFX_Emoticon_Motion_Angry.wav";
        };

        if (this.emoticon.playSound()) {
            AudioManager.getInstance().play(location, false, true);
        }

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

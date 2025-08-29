package com.bascenario.engine.scenario.event.impl.sound;

import com.bascenario.audio.AudioManager;
import com.bascenario.engine.scenario.elements.Sound;
import com.bascenario.engine.scenario.event.api.Event;
import com.bascenario.engine.scenario.screen.ScenarioScreen;

public class PlaySoundEvent extends Event {
    private final Sound sound;
    private final boolean loop;
    public PlaySoundEvent(Sound sound, boolean loop) {
        super(0);
        this.sound = sound;
        this.loop = loop;
    }

    @Override
    public void onStart(ScenarioScreen screen) {
        AudioManager.getInstance().play(this.sound.path(), this.loop);
    }
}

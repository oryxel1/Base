package com.bascenario.engine.scenario.event.impl.sound;

import com.bascenario.audio.AudioManager;
import com.bascenario.engine.scenario.elements.Sound;
import com.bascenario.engine.scenario.event.api.Event;
import com.bascenario.engine.scenario.screen.ScenarioScreen;

public class StopSoundEvent extends Event {
    private final Sound sound;
    private final boolean fade;
    private final long fadeDuration;

    public StopSoundEvent(Sound sound, boolean fade, long fadeDuration) {
        super(fadeDuration + 100L);
        this.sound = sound;
        this.fade = fade;
        this.fadeDuration = fadeDuration;
    }

    public StopSoundEvent(Sound sound) {
        super(0);
        this.sound = sound;
        this.fade = false;
        this.fadeDuration = 0;
    }

    @Override
    public void onStart(ScenarioScreen screen) {
        if (this.fade) {
            AudioManager.getInstance().fadeOut(this.sound.path(), this.fadeDuration);
        } else {
            AudioManager.getInstance().stop(this.sound.path());
        }
    }
}

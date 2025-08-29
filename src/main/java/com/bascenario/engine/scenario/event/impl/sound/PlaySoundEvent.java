package com.bascenario.engine.scenario.event.impl.sound;

import com.bascenario.audio.AudioManager;
import com.bascenario.engine.scenario.elements.Sound;
import com.bascenario.engine.scenario.event.api.Event;
import com.bascenario.engine.scenario.screen.ScenarioScreen;
import net.raphimc.thingl.implementation.window.WindowInterface;
import org.joml.Matrix4fStack;

public class PlaySoundEvent extends Event {
    private final Sound sound;
    private final boolean loop;

    public PlaySoundEvent(Sound sound, boolean loop) {
        super(sound.fadeIn() > 0 ? sound.fadeIn() + 100 : 0);
        this.sound = sound;
        this.loop = loop;
    }

    @Override
    public void render(ScenarioScreen screen, long time, Matrix4fStack positionMatrix, WindowInterface window) {
        if (sound.fadeIn() > 0) {
            AudioManager.getInstance().fadeIn(this.sound.path(), this.sound.fadeIn(), this.loop);
        }
    }

    @Override
    public void onStart(ScenarioScreen screen) {
        if (sound.fadeIn() <= 0) {
            AudioManager.getInstance().play(this.sound.path(), this.loop);
        }
    }
}

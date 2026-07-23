package oxy.base.event.impl.sound;

import oxy.base.api.event.sound.PlaySoundEvent;
import oxy.base.event.base.FunctionEvent;
import oxy.base.managers.AudioManager;
import oxy.base.screens.ScenarioScreen;

public class FunctionPlaySoundEvent extends FunctionEvent<PlaySoundEvent> {
    public FunctionPlaySoundEvent(PlaySoundEvent event) {
        super(event);
    }

    @Override
    public void run(ScenarioScreen screen) {
        AudioManager.getInstance().play(screen.getScenario().getName(), event.sound(), event.start(), event.duration());
    }
}

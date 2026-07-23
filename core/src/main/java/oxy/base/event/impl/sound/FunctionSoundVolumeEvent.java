package oxy.base.event.impl.sound;

import oxy.base.api.event.sound.SoundVolumeEvent;
import oxy.base.event.base.FunctionEvent;
import oxy.base.managers.AudioManager;
import oxy.base.screens.ScenarioScreen;

public class FunctionSoundVolumeEvent extends FunctionEvent<SoundVolumeEvent> {
    public FunctionSoundVolumeEvent(SoundVolumeEvent event) {
        super(event);
    }

    @Override
    public void run(ScenarioScreen screen) {
        AudioManager.getInstance().fade(event.id(), event.duration(), event.volume(), event.easing());
    }
}

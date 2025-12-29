package oxy.bascenario.event.impl.sound;

import oxy.bascenario.api.event.sound.SoundVolumeEvent;
import oxy.bascenario.event.base.FunctionEvent;
import oxy.bascenario.managers.AudioManager;
import oxy.bascenario.screens.ScenarioScreen;

public class FunctionSoundVolumeEvent extends FunctionEvent<SoundVolumeEvent> {
    public FunctionSoundVolumeEvent(SoundVolumeEvent event) {
        super(event);
    }

    @Override
    public void run(ScenarioScreen screen) {
        AudioManager.getInstance().fade(event.id(), event.duration(), event.volume(), event.easing());
    }
}

package oxy.base.event.impl.sound;

import oxy.base.api.event.sound.StopSoundEvent;
import oxy.base.event.base.FunctionEvent;
import oxy.base.managers.AudioManager;
import oxy.base.screens.ScenarioScreen;

public class FunctionStopSoundEvent extends FunctionEvent<StopSoundEvent> {
    public FunctionStopSoundEvent(StopSoundEvent event) {
        super(event);
    }

    @Override
    public void run(ScenarioScreen screen) {
        AudioManager.getInstance().stop(event.id(), event.duration());
    }
}

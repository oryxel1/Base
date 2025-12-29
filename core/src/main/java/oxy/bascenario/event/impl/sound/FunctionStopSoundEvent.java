package oxy.bascenario.event.impl.sound;

import oxy.bascenario.api.event.sound.StopSoundEvent;
import oxy.bascenario.event.base.FunctionEvent;
import oxy.bascenario.managers.AudioManager;
import oxy.bascenario.screens.ScenarioScreen;

public class FunctionStopSoundEvent extends FunctionEvent<StopSoundEvent> {
    public FunctionStopSoundEvent(StopSoundEvent event) {
        super(event);
    }

    @Override
    public void run(ScenarioScreen screen) {
        AudioManager.getInstance().stop(event.id(), event.duration());
    }
}

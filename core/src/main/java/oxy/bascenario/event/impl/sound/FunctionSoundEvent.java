package oxy.bascenario.event.impl.sound;

import oxy.bascenario.api.event.sound.SoundEvent;
import oxy.bascenario.event.base.FunctionEvent;
import oxy.bascenario.managers.AudioManager;
import oxy.bascenario.screens.ScenarioScreen;

public class FunctionSoundEvent extends FunctionEvent<SoundEvent> {
    public FunctionSoundEvent(SoundEvent event) {
        super(event);
    }

    @Override
    public void run(ScenarioScreen screen) {
        switch (event.getEvent()) {
            case STOP -> AudioManager.getInstance().stop(event.getId());
            case PAUSE -> AudioManager.getInstance().pause(event.getId());
            case RESUME -> AudioManager.getInstance().resume(event.getId());
        }
    }
}

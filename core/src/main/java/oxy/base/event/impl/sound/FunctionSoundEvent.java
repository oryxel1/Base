package oxy.base.event.impl.sound;

import oxy.base.api.event.sound.SoundEvent;
import oxy.base.event.base.FunctionEvent;
import oxy.base.managers.AudioManager;
import oxy.base.screens.ScenarioScreen;

public class FunctionSoundEvent extends FunctionEvent<SoundEvent> {
    public FunctionSoundEvent(SoundEvent event) {
        super(event);
    }

    @Override
    public void run(ScenarioScreen screen) {
        switch (event.event()) {
            case PAUSE -> AudioManager.getInstance().pause(event.id());
            case RESUME -> AudioManager.getInstance().resume(event.id());
        }
    }
}

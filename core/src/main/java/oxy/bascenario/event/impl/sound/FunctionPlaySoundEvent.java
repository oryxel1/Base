package oxy.bascenario.event.impl.sound;

import oxy.bascenario.api.event.sound.PlaySoundEvent;
import oxy.bascenario.event.base.FunctionEvent;
import oxy.bascenario.managers.AudioManager;
import oxy.bascenario.screens.ScenarioScreen;

public class FunctionPlaySoundEvent extends FunctionEvent<PlaySoundEvent> {
    public FunctionPlaySoundEvent(PlaySoundEvent event) {
        super(event);
    }

    @Override
    public void run(ScenarioScreen screen) {
        AudioManager.getInstance().play(screen.getScenario(), event.getSound(), event.getStart(), event.getDuration());
    }
}

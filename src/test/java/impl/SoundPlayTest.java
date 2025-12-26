package impl;

import oxy.bascenario.api.Scenario;
import oxy.bascenario.api.effects.Sound;
import oxy.bascenario.api.event.sound.PlaySoundEvent;
import oxy.bascenario.api.utils.FileInfo;
import oxy.bascenario.screens.ScenarioScreen;
import oxy.bascenario.utils.Launcher;

public class SoundPlayTest {
    public static void main(String[] args) {
        final Scenario.Builder scenario = new Scenario.Builder();
        scenario.add(0, new PlaySoundEvent(
                new Sound(0, new FileInfo("random_noise.wav", false, true), 1, true), 0, 0
        ));
        Launcher.launch(new ScenarioScreen(scenario.build()), false);
    }
}

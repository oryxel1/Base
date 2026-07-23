import oxy.base.api.Scenario;
import oxy.base.api.effects.Sound;
import oxy.base.api.event.sound.PlaySoundEvent;
import oxy.base.api.utils.FileInfo;
import oxy.base.screens.ScenarioScreen;
import oxy.base.utils.Launcher;

public class SoundPlayTest {
    public static void main(String[] args) {
        final Scenario.Builder scenario = new Scenario.Builder();
        scenario.add(0, new PlaySoundEvent(
                new Sound(0, new FileInfo("random_noise.wav", false, true), 1, true), 0, 0
        ));
        Launcher.launch(new ScenarioScreen(scenario.build()), false);
    }
}

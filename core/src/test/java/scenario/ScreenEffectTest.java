package scenario;

import oxy.base.api.Scenario;
import oxy.base.api.effects.ScreenEffect;
import oxy.base.api.event.ScreenEffectEvent;
import oxy.base.api.event.background.SetBackgroundEvent;
import oxy.base.api.utils.FileInfo;
import oxy.base.screens.ScenarioScreen;
import oxy.base.utils.Launcher;

public class ScreenEffectTest {
    public static void main(String[] args) {
        final Scenario.Builder scenario = new Scenario.Builder();

        scenario.add(0, new SetBackgroundEvent(FileInfo.internal("BG_BlackMarket.jpg"), 0));
        scenario.add(0, new ScreenEffectEvent(ScreenEffectEvent.Type.ADD, ScreenEffect.TV_NOISE));

        Launcher.launch(new ScenarioScreen(scenario.build()), false);
    }
}

package scenario;

import oxy.bascenario.api.Scenario;
import oxy.bascenario.api.effects.ScreenEffect;
import oxy.bascenario.api.effects.TransitionType;
import oxy.bascenario.api.event.ScreenEffectEvent;
import oxy.bascenario.api.event.ScreenTransitionEvent;
import oxy.bascenario.api.event.background.SetBackgroundEvent;
import oxy.bascenario.api.utils.FileInfo;
import oxy.bascenario.screens.ScenarioScreen;
import oxy.bascenario.utils.Launcher;

public class ScreenEffectTest {
    public static void main(String[] args) {
        final Scenario.Builder scenario = new Scenario.Builder();

        scenario.add(0, new SetBackgroundEvent(FileInfo.internal("BG_BlackMarket.jpg"), 0));
        scenario.add(0, new ScreenEffectEvent(ScreenEffectEvent.Type.ADD, ScreenEffect.FILM_GRAIN));

        Launcher.launch(new ScenarioScreen(scenario.build()), false);
    }
}

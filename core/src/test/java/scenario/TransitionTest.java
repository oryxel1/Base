package scenario;

import oxy.bascenario.api.Scenario;
import oxy.bascenario.api.effects.TransitionType;
import oxy.bascenario.api.event.ScreenTransitionEvent;
import oxy.bascenario.api.event.background.SetBackgroundEvent;
import oxy.bascenario.api.utils.FileInfo;
import oxy.bascenario.screens.ScenarioScreen;
import oxy.bascenario.utils.Launcher;

public class TransitionTest {
    public static void main(String[] args) {
        final Scenario.Builder scenario = new Scenario.Builder();

        scenario.add(0, new SetBackgroundEvent(FileInfo.internal("BG_BlackMarket.jpg"), 0));
        scenario.add(500, new ScreenTransitionEvent(FileInfo.internal("BG_ShoppingMall.jpg"), TransitionType.BACKGROUND_OVERLAP, 1000, 700, 1700));

        Launcher.launch(new ScenarioScreen(scenario.build()), false);
    }
}

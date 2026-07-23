package scenario;

import oxy.base.api.Scenario;
import oxy.base.api.effects.TransitionType;
import oxy.base.api.event.ScreenTransitionEvent;
import oxy.base.api.event.background.SetBackgroundEvent;
import oxy.base.api.utils.FileInfo;
import oxy.base.screens.ScenarioScreen;
import oxy.base.utils.Launcher;

public class TransitionTest {
    public static void main(String[] args) {
        final Scenario.Builder scenario = new Scenario.Builder();

        scenario.add(0, new SetBackgroundEvent(FileInfo.internal("BG_BlackMarket.jpg"), 0));
        scenario.add(500, new ScreenTransitionEvent(FileInfo.internal("BG_ShoppingMall.jpg"), TransitionType.BACKGROUND_OVERLAP, 1000, 700, 1700));

        Launcher.launch(new ScenarioScreen(scenario.build()), false);
    }
}

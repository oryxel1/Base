import oxy.bascenario.Base;
import oxy.bascenario.api.Scenario;
import oxy.bascenario.api.event.ShowButtonsEvent;
import oxy.bascenario.screens.ScenarioScreen;
import oxy.bascenario.utils.ExtendableScreen;
import oxy.bascenario.utils.Launcher;
import oxy.bascenario.utils.thingl.ThinGLUtils;
import oxy.bascenario.utils.thingl.other.TilingSprite;

public class DummyTest {
    public static void main(String[] args) {
        final Scenario.Builder scenario = new Scenario.Builder();

        scenario.add(0, new ShowButtonsEvent(true));
        Launcher.launch(new ScenarioScreen(scenario.build()), false);
    }
}
import oxy.bascenario.utils.Launcher;
import oxy.bascenario.api.Scenario;
import oxy.bascenario.screens.ScenarioPreviewScreen;

public class ScenarioPreviewTest {
    public static void main(String[] args) {
        final Scenario.Builder scenario = new Scenario.Builder();
        scenario.title("This is a simple title");
        scenario.subtitle("Episode: Test");

        Launcher.launch(new ScenarioPreviewScreen(scenario.build()), false);
    }
}

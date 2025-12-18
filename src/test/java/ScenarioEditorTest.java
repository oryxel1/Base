import oxy.bascenario.api.Scenario;
import oxy.bascenario.editor.ScenarioEditorScreen;
import oxy.bascenario.utils.Launcher;

public class ScenarioEditorTest {
    public static void main(String[] args) {
        final Scenario.Builder scenario = new Scenario.Builder();
        scenario.title("Test");
        scenario.subtitle("Test");

        Launcher.launch(new ScenarioEditorScreen(scenario), false);
    }
}

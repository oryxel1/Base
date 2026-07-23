import oxy.base.api.Scenario;
import oxy.base.editor.screen.ScenarioEditorScreen;
import oxy.base.utils.Launcher;

public class ScenarioEditorTest {
    public static void main(String[] args) {
        final Scenario.Builder scenario = new Scenario.Builder();
        scenario.name("Scenario Editor Test");

        Launcher.launch(new ScenarioEditorScreen(null, scenario.build(), scenario), false);
    }
}

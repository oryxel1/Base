import oxy.bascenario.Base;
import oxy.bascenario.api.Scenario;
import oxy.bascenario.editor.screen.ScenarioEditorScreen;
import oxy.bascenario.utils.Launcher;

public class ScenarioEditorTest {
    public static void main(String[] args) {
        final Scenario.Builder scenario = new Scenario.Builder();
        scenario.name("EditorTest");
        Base.instance().scenarioManager().put("EditorTest", scenario.build());

        Launcher.launch(new ScenarioEditorScreen(scenario.build(), scenario), false);
    }
}

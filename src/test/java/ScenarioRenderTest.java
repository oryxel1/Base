import com.bascenario.engine.scenario.Scenario;
import com.bascenario.engine.scenario.screen.ScenarioPreviewScreen;
import com.bascenario.launcher.Launcher;

public class ScenarioRenderTest {
    public static void main(String[] args) {
        Launcher.WINDOW.setCurrentScreen(new ScenarioPreviewScreen(Scenario.builder()
                .name("A Weird Scenario")
                .build()));
        Launcher.main(args);
    }
}

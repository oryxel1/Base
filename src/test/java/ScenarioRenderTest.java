import com.bascenario.engine.scenario.Scenario;
import com.bascenario.engine.scenario.screen.ScenarioPreviewScreen;
import com.bascenario.launcher.Launcher;

public class ScenarioRenderTest {
    public static void main(String[] args) {
        Scenario.Background background = new Scenario.Background(
                "C:\\Users\\PC\\Downloads\\output\\MediaResources\\GameData\\UIs\\03_Scenario\\01_Background\\BG_StudentRoom.jpg", 0, -1,
                false, false);

        Scenario scenario = Scenario.builder()
                .name("A Weird Scenario").previewBackground(background)
                .build();
        scenario.getBackgrounds().add(background);

        Launcher.WINDOW.setCurrentScreen(new ScenarioPreviewScreen(scenario));
        Launcher.main(args);
    }
}

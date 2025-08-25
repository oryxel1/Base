import com.bascenario.engine.scenario.Scenario;
import com.bascenario.engine.scenario.screen.ScenarioPreviewScreen;
import com.bascenario.launcher.Launcher;

public class ScenarioRenderTest {
    public static void main(String[] args) {
        Scenario scenario = Scenario.builder()
                .name("A Weird Scenario")
                .build();
        scenario.getBackgrounds().add(new Scenario.Background("Room A",
                "C:\\Users\\PC\\Downloads\\output\\MediaResources\\GameData\\UIs\\03_Scenario\\01_Background\\BG_StudentRoom.jpg", 0, -1));

        Launcher.WINDOW.setCurrentScreen(new ScenarioPreviewScreen(scenario));
        Launcher.main(args);
    }
}

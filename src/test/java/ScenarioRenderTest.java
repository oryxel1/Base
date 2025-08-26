import com.bascenario.engine.scenario.Scenario;
import com.bascenario.engine.scenario.event.impl.LocationInfoEvent;
import com.bascenario.engine.scenario.screen.ScenarioPreviewScreen;
import com.bascenario.engine.scenario.screen.ScenarioScreen;
import com.bascenario.launcher.Launcher;
import com.bascenario.render.MainRendererWindow;

import java.util.List;

public class ScenarioRenderTest {
    public static void main(String[] args) {
        Scenario.Background background = new Scenario.Background(
                "C:\\Users\\PC\\Downloads\\output\\MediaResources\\GameData\\UIs\\03_Scenario\\01_Background\\BG_RiversideRoad.jpg", 0, -1,
                false, false);

        Scenario scenario = Scenario.builder()
                .name("Warm Sunshine").previewBackground(background)
                .build();
        scenario.getBackgrounds().add(background);

        scenario.getTimestamps().add(new Scenario.Timestamp(
                200L, List.of(new LocationInfoEvent("A Riverside Road on the Outskirts of Gehenna"))
        ));

        if (args.length > 0 && args[0].equals("skip-preview")) {
            Launcher.launch(new ScenarioScreen(scenario));
        } else {
            Launcher.launch(new ScenarioPreviewScreen(scenario));
        }
    }
}

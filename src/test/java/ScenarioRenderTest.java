import com.bascenario.engine.scenario.Scenario;
import com.bascenario.engine.scenario.event.impl.LocationInfoEvent;
import com.bascenario.engine.scenario.screen.ScenarioPreviewScreen;
import com.bascenario.engine.scenario.screen.ScenarioScreen;
import com.bascenario.launcher.Launcher;
import com.bascenario.render.MainRendererWindow;

import java.util.ArrayList;
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

        final List<Scenario.Dialogue> dialogues = new ArrayList<>();
        dialogues.add(Scenario.Dialogue.builder().dialogue("...Hi, Sensei.")
                .time(200L).name("Hina").association("Prefect Team")
                .playSpeed(1).textSize(-1).fontType(Scenario.FontType.REGULAR)
                .cutscene(false)
                .build());

        dialogues.add(Scenario.Dialogue.builder().dialogue("Hmm?")
                .time(0L).name("Hina").association("Prefect Team")
                .playSpeed(1).textSize(-1).fontType(Scenario.FontType.REGULAR)
                .cutscene(false)
                .build());
        scenario.getDialogues().put(0, dialogues);

        dialogues.add(Scenario.Dialogue.builder().dialogue("...")
                .time(0L).name("Hina").association("Prefect Team")
                .playSpeed(1).textSize(-1).fontType(Scenario.FontType.REGULAR)
                .cutscene(false)
                .build());
        scenario.getDialogues().put(0, dialogues);

        boolean fullScreen = args.length > 1 && args[1].equals("fullscreen");
        if (args.length > 0 && args[0].equals("skip-preview")) {
            Launcher.launch(new ScenarioScreen(scenario), fullScreen);
        } else {
            Launcher.launch(new ScenarioPreviewScreen(scenario), fullScreen);
        }
    }
}

import com.bascenario.engine.scenario.Scenario;
import com.bascenario.engine.scenario.event.impl.LocationInfoEvent;
import com.bascenario.engine.scenario.event.impl.RedirectDialogueEvent;
import com.bascenario.engine.scenario.screen.ScenarioPreviewScreen;
import com.bascenario.engine.scenario.screen.ScenarioScreen;
import com.bascenario.launcher.Launcher;
import com.bascenario.render.MainRendererWindow;

import java.util.*;

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
        dialogues.add(Scenario.Dialogue.builder().dialogue("Hi, Sensei.")
                .time(201L).name("Hina").association("Prefect Team")
                .playSpeed(1).textSize(-1).fontType(Scenario.FontType.REGULAR)
                .cutscene(false)
                .build());

        final LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("\"What's with the scowl, Hina?\"", 0);
        map.put("\"Why so serious?\"", 0);
        scenario.getDialogueOptions().add(new Scenario.DialogueOptions(201L, map));

        dialogues.add(Scenario.Dialogue.builder().dialogue("Hmm?")
                .time(202L).name("Hina").association("Prefect Team")
                .playSpeed(1).textSize(-1).fontType(Scenario.FontType.REGULAR)
                .cutscene(false)
                .build());
        scenario.getDialogues().put(0, dialogues);

        dialogues.add(Scenario.Dialogue.builder().dialogue("...")
                .time(202L).name("Hina").association("Prefect Team")
                .playSpeed(1).textSize(-1).fontType(Scenario.FontType.REGULAR)
                .cutscene(false)
                .build());

        dialogues.add(Scenario.Dialogue.builder().dialogue("My apologies. I was up all night doing some work.")
                .time(203L).name("Hina").association("Prefect Team")
                .playSpeed(1).textSize(-1).fontType(Scenario.FontType.REGULAR)
                .cutscene(false)
                .build());

        dialogues.add(Scenario.Dialogue.builder().dialogue("I still haven't slept, and I have bags under my eyes. Not to mention my hair is a mess since I didn't wash it...")
                .time(204L).name("Hina").association("Prefect Team")
                .playSpeed(1).textSize(-1).fontType(Scenario.FontType.REGULAR)
                .cutscene(false)
                .build());

        dialogues.add(Scenario.Dialogue.builder().dialogue("It's your fault for asking me to meet you like this!")
                .time(205L).name("Hina").association("Prefect Team")
                .playSpeed(1).textSize(-1).fontType(Scenario.FontType.REGULAR)
                .cutscene(false)
                .build());

        final LinkedHashMap<String, Integer> map1 = new LinkedHashMap<>();
        map1.put("Ask Hina to take a break because she looks like she's about to fall over.", 1);
        map1.put("Ask Hina to sit on a bench and photosynthesize with you.", 2);
        scenario.getDialogueOptions().add(new Scenario.DialogueOptions(205L, map1));
        scenario.getDialogues().put(1, List.of(Scenario.Dialogue.builder().dialogue("What? You just want to sit on a bench and take in the sun...?")
                .time(206L).name("Hina").association("Prefect Team")
                .playSpeed(1).textSize(-1).fontType(Scenario.FontType.REGULAR)
                .cutscene(false)
                .build()));
        scenario.getDialogues().put(2, List.of(Scenario.Dialogue.builder().dialogue("Photosynthesize...? What am I, a plant?")
                .time(206L).name("Hina").association("Prefect Team")
                .playSpeed(1).textSize(-1).fontType(Scenario.FontType.REGULAR)
                .cutscene(false)
                .build()));
        scenario.getTimestamps().add(new Scenario.Timestamp(206L, List.of(new RedirectDialogueEvent(0))));

        dialogues.add(Scenario.Dialogue.builder().dialogue("I'll give you that the weather is nice today...")
                .time(206L).name("Hina").association("Prefect Team")
                .playSpeed(1).textSize(-1).fontType(Scenario.FontType.REGULAR)
                .cutscene(false)
                .build());

        scenario.getDialogues().put(0, dialogues);

        scenario.getSounds().add(new Scenario.Sound("C:\\Users\\PC\\Downloads\\Track_10_Mitsukiyo_Romantic_Smile.ogg",
                -1, 700L, false, true));

        System.out.println(Arrays.toString(scenario.getDialogueOptions().toArray()));

        boolean fullScreen = args.length > 1 && args[1].equals("fullscreen") || args.length > 0 && args[0].equals("fullscreen");
        if (args.length > 0 && args[0].equals("skip-preview")) {
            Launcher.launch(new ScenarioScreen(scenario), fullScreen);
        } else {
            Launcher.launch(new ScenarioPreviewScreen(scenario), fullScreen);
        }
    }
}

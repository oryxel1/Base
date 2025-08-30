import com.bascenario.engine.scenario.Scenario;
import com.bascenario.engine.scenario.elements.*;
import com.bascenario.engine.scenario.event.impl.*;
import com.bascenario.engine.scenario.event.impl.dialogue.PlayDialogueEvent;
import com.bascenario.engine.scenario.event.impl.dialogue.RedirectDialogueEvent;
import com.bascenario.engine.scenario.event.impl.dialogue.ShowDialogueOptionEvent;
import com.bascenario.engine.scenario.event.impl.sound.PlaySoundEvent;
import com.bascenario.engine.scenario.event.impl.sound.StopSoundEvent;
import com.bascenario.engine.scenario.event.impl.sprite.AddSpriteEvent;
import com.bascenario.engine.scenario.event.impl.sprite.SpriteAnimationEvent;
import com.bascenario.engine.scenario.event.impl.sprite.SpriteLocationEvent;
import com.bascenario.engine.scenario.screen.ScenarioPreviewScreen;
import com.bascenario.engine.scenario.screen.ScenarioScreen;
import com.bascenario.launcher.Launcher;

import java.util.*;

public class ScenarioRenderTest {
    public static void main(String[] args) {
        Background background = new Background(
                "C:\\Users\\PC\\Downloads\\output\\MediaResources\\GameData\\UIs\\03_Scenario\\01_Background\\BG_RiversideRoad.jpg",
                false, false);

        final Sound previewSound = new Sound("C:\\Users\\PC\\Downloads\\Track_10_Mitsukiyo_Romantic_Smile.ogg", -1);
        Scenario scenario = Scenario.builder()
                .name("Warm Sunshine").previewBackground(background).previewSound(previewSound)
                .build();

        final Sprite hinaSprite = new Sprite("C:\\Users\\PC\\Downloads\\hina_spr.skel", "C:\\Users\\PC\\Downloads\\hina_spr.atlas", "Idle_01", true);
        scenario.add(0,
                new StopSoundEvent(previewSound, true, 200L),
                new SetBackgroundEvent(background),
                new AddSpriteEvent(hinaSprite),
                new SpriteLocationEvent(0, hinaSprite, 0, 50),
                new SpriteAnimationEvent(hinaSprite, "15", 1, true)
        );

        scenario.add(200, new LocationInfoEvent("A Riverside Road on the Outskirts of Gehenna"));
//        scenario.add(0, new LockClickEvent(true));
        scenario.add(0, new PlayDialogueEvent(Dialogue.builder().dialogue("Hi, Sensei.")
                .name("Hina").association("Prefect Team")
                .playSpeed(1).textScale(-1).fontType(Dialogue.FontType.REGULAR)
                .cutscene(false)
                .build()));

        {
            final LinkedHashMap<String, Integer> options = new LinkedHashMap<>();
            options.put("\"What's with the scowl, Hina?\"", 0);
            options.put("\"Why so serious?\"", 0);

            scenario.add(true, 1, new ShowDialogueOptionEvent(new DialogueOptions(options)));
        }

        scenario.add(true, 1, new PlayDialogueEvent(Dialogue.builder().dialogue("Hmm?")
                .name("Hina").association("Prefect Team")
                .playSpeed(1).textScale(-1).fontType(Dialogue.FontType.REGULAR)
                .cutscene(false)
                .build()));
        scenario.add(true, 1, new PlayDialogueEvent(Dialogue.builder().dialogue("...")
                .name("Hina").association("Prefect Team")
                .playSpeed(1).textScale(-1).fontType(Dialogue.FontType.REGULAR)
                .cutscene(false)
                .build()));

        scenario.add(true, 1,
                new PlaySoundEvent(new Sound("C:\\Users\\PC\\Downloads\\SE_Run_03.wav", -1), false),
                new SpriteLocationEvent(800, hinaSprite, 300, 50)
        );
        scenario.add(800, new SpriteAnimationEvent(hinaSprite, "02", 1, true));
        scenario.add(400,
                new PlaySoundEvent(new Sound("C:\\Users\\PC\\Downloads\\SE_Run_04.wav", -1), false),
                new SpriteLocationEvent(800, hinaSprite, 0, 50)
        );

        scenario.add(true, 1,
                new PlaySoundEvent(new Sound("C:\\Users\\PC\\Downloads\\Track_10_Mitsukiyo_Romantic_Smile.ogg", 1500L), false),
                new PlayDialogueEvent(Dialogue.builder().dialogue("My apologies. I was up all night doing some work.")
                .name("Hina").association("Prefect Team")
                .playSpeed(1).textScale(-1).fontType(Dialogue.FontType.REGULAR)
                .cutscene(false)
                .build())
        );
        scenario.add(true, 1,
                new SpriteAnimationEvent(hinaSprite, "04", 1, true),
                new PlayDialogueEvent(Dialogue.builder().dialogue("I still haven't slept, and I have bags under my eyes. Not to mention my hair is a mess since I didn't wash it...")
                        .name("Hina").association("Prefect Team")
                        .playSpeed(1).textScale(-1).fontType(Dialogue.FontType.REGULAR)
                        .cutscene(false)
                        .build())
        );

        scenario.add(true, 1,
                new SpriteAnimationEvent(hinaSprite, "06", 1, true),
                new PlayDialogueEvent(Dialogue.builder().dialogue("It's your fault for asking me to meet you like this!")
                        .name("Hina").association("Prefect Team")
                        .playSpeed(1).textScale(-1).fontType(Dialogue.FontType.REGULAR)
                        .cutscene(false)
                        .build())
        );

        {
            final LinkedHashMap<String, Integer> options = new LinkedHashMap<>();
            options.put("Ask Hina to take a break because she looks like she's about to fall over", 1);
            options.put("Ask Hina to sit on a bench and photosynthesize with you", 2);

            scenario.add(true, 1, new ShowDialogueOptionEvent(new DialogueOptions(options)));
        }

        scenario.add(true, 1,
                new SpriteAnimationEvent(hinaSprite, "14", 1, true),

                new PlayDialogueEvent(Dialogue.builder().dialogue("What? You just want to sit on a bench and take in the sun...?")
                        .name("Hina").association("Prefect Team")
                        .playSpeed(1).textScale(-1).fontType(Dialogue.FontType.REGULAR)
                        .cutscene(false).index(1)
                        .build()),

                new PlayDialogueEvent(Dialogue.builder().dialogue("Photosynthesize...? What am I, a plant?")
                        .name("Hina").association("Prefect Team")
                        .playSpeed(1).textScale(-1).fontType(Dialogue.FontType.REGULAR)
                        .cutscene(false).index(2)
                        .build()),

                new RedirectDialogueEvent(0)
        );

        scenario.add(true, 1,
                new SpriteAnimationEvent(hinaSprite, "01", 1, true),
                new PlayDialogueEvent(Dialogue.builder().dialogue("I'll give you that the weather is nice today...")
                        .name("Hina").association("Prefect Team")
                        .playSpeed(1).textScale(-1).fontType(Dialogue.FontType.REGULAR)
                        .cutscene(false)
                        .build())
        );

        scenario.add(true, 1,
                new SpriteLocationEvent(600L, hinaSprite, -20, 50),
                new PlayDialogueEvent(Dialogue.builder().dialogue("(move)")
                        .name("Hina").association("Prefect Team")
                        .playSpeed(1).textScale(-1).fontType(Dialogue.FontType.REGULAR)
                        .cutscene(false)
                        .build())
        );

        scenario.add(true, 1, new PlayDialogueEvent(Dialogue.builder().dialogue("Hina and I found a bench and sat side-by-side.")
                .name("").association("")
                .playSpeed(1).textScale(-1).fontType(Dialogue.FontType.REGULAR)
                .cutscene(false)
                .build()));

//        System.out.println(scenario.toJson().toString());

        boolean fullScreen = args.length > 1 && args[1].equals("fullscreen") || args.length > 0 && args[0].equals("fullscreen");
        if (args.length > 0 && args[0].equals("skip-preview")) {
            Launcher.launch(new ScenarioScreen(scenario), fullScreen);
        } else {
            Launcher.launch(new ScenarioPreviewScreen(scenario), fullScreen);
        }
    }
}

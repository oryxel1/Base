import com.bascenario.engine.scenario.Scenario;
import com.bascenario.engine.scenario.elements.*;
import com.bascenario.engine.scenario.event.impl.*;
import com.bascenario.engine.scenario.event.impl.dialogue.PlayDialogueEvent;
import com.bascenario.engine.scenario.event.impl.dialogue.ShowDialogueOptionEvent;
import com.bascenario.engine.scenario.event.impl.sound.PlaySoundEvent;
import com.bascenario.engine.scenario.event.impl.sprite.AddSpriteEvent;
import com.bascenario.engine.scenario.event.impl.sprite.SpriteAnimationEvent;
import com.bascenario.engine.scenario.event.impl.sprite.SpriteLocationEvent;
import com.bascenario.engine.scenario.event.impl.sprite.mini.SpriteShakeEvent;
import com.bascenario.engine.scenario.screen.ScenarioPreviewScreen;
import com.bascenario.engine.scenario.screen.ScenarioScreen;
import com.bascenario.launcher.Launcher;

import java.util.LinkedHashMap;
import java.util.Map;

public class ScenarioRenderTest {
    public static void main(String[] args) {
        Background background = new Background(
                "C:\\Users\\PC\\Downloads\\output\\MediaResources\\GameData\\MediaResources\\UIs\\03_Scenario\\01_Background\\BG_GehennaCampus_Night.jpg",
                false, false);

        Scenario scenario = Scenario.builder()
                .name("Hesitant Yet Eager to Share").previewBackground(background)
                .build();

        scenario.add(0, new SetBackgroundEvent(background),
                new PlayDialogueEvent(Dialogue.builder()
                        .name("").association("")
                        .textScale(-1).playSpeed(1)
                        .dialogue("I made plans to meet with Hina on Valentine's Day evening.")
                        .closeOnClick(true)
                        .build())
        );
        scenario.add(true, 1, new ShowDialogueOptionEvent(
                DialogueOptions.builder()
                        .options(Map.of("\"She said to meet somewhere around here...\"", 0))
                        .build()
        ));
        scenario.add(true, 1, new PlaySoundEvent(
                new Sound("C:\\Users\\PC\\Downloads\\SE_FootStep_02.wav", -1), false
        ));

        final Sprite hinaSprite = new Sprite("C:\\Users\\PC\\Downloads\\hina_spr.skel", "C:\\Users\\PC\\Downloads\\hina_spr.atlas", "Idle_01", true);
        scenario.add(2000L,
                new PlayDialogueEvent(Dialogue.builder()
                .name("Hina").association("Prefect Team")
                .textScale(-1).playSpeed(1)
                .dialogue("Oh... Hello, Sensei.")
                .build()),

                new AddSpriteEvent(hinaSprite),
                new SpriteAnimationEvent(hinaSprite, "12", 1, true),
                new SpriteLocationEvent(0, hinaSprite, 100, 50),
                new SpriteLocationEvent(1000L, hinaSprite, 20, 50),
                new QueueEventEvent(800L, new PlayEmoticonEvent(hinaSprite, new Sprite.Emoticon(600L,
                        2200L, 800,-360, Sprite.EmoticonType.SWEAT)))
        );
        scenario.add(true, 1, new ShowDialogueOptionEvent(
                DialogueOptions.builder()
                        .options(Map.of("\"Hina!\"", 0))
                        .build()
        ));
        scenario.add(true, 1,
                new SpriteAnimationEvent(hinaSprite, "11", 1, true),
                new SpriteLocationEvent(300L, hinaSprite, 0, 50),
                new PlayDialogueEvent(Dialogue.builder()
                        .name("Hina").association("Prefect Team")
                        .textScale(-1).playSpeed(1)
                        .dialogue("D-Don't shout like that!")
                        .build())
        );
        scenario.add(true, 1,
                new SpriteAnimationEvent(hinaSprite, "04", 1, true),
                new PlayDialogueEvent(Dialogue.builder()
                        .name("Hina").association("Prefect Team")
                        .textScale(-1).playSpeed(1)
                        .dialogue("Please keep your voice down. I snuck away from duties.\nThe last thing I want is for us to draw unwanted attention.")
                        .build())
        );
        scenario.add(true, 1,
                new SpriteAnimationEvent(hinaSprite, "14", 1, true),
                new PlayEmoticonEvent(hinaSprite, new Sprite.Emoticon(600L, 2200L, 800,-360, Sprite.EmoticonType.SWEAT)),
                new SpriteShakeEvent(500L, 60L, hinaSprite),
                new PlayDialogueEvent(Dialogue.builder()
                        .name("Hina").association("Prefect Team")
                        .textScale(-1).playSpeed(1)
                        .dialogue("I can't imagine you do either.")
                        .build())
        );

        {
            final Map<String, Integer> options = new LinkedHashMap<>();
            options.put("\"I have nothing to hide when it comes to you.\"", 0);
            options.put("\"You're worth any hassle that comes my way.\"", 0);

            scenario.add(true, 1, new ShowDialogueOptionEvent(new DialogueOptions(options)));
        }

        scenario.add(true, 1,
                new SpriteAnimationEvent(hinaSprite, "04", 1, true),
                new PlayDialogueEvent(Dialogue.builder()
                        .name("Hina").association("Prefect Team")
                        .textScale(-1).playSpeed(1)
                        .dialogue("*sigh* What does someone say to something like that?")
                        .build())
        );

        boolean fullScreen = args.length > 1 && args[1].equals("fullscreen") || args.length > 0 && args[0].equals("fullscreen");
        if (args.length > 0 && args[0].equals("skip-preview")) {
            Launcher.launch(new ScenarioScreen(scenario), fullScreen);
        } else {
            Launcher.launch(new ScenarioPreviewScreen(scenario), fullScreen);
        }
    }
}

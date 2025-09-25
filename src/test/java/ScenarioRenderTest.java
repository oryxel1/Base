import com.bascenario.Launcher;
import com.bascenario.engine.scenario.Scenario;
import com.bascenario.engine.scenario.elements.*;
import com.bascenario.engine.scenario.event.impl.*;
import com.bascenario.engine.scenario.event.impl.background.SetBackgroundEvent;
import com.bascenario.engine.scenario.event.impl.dialogue.PlayDialogueEvent;
import com.bascenario.engine.scenario.event.impl.dialogue.ShowDialogueOptionEvent;
import com.bascenario.engine.scenario.event.impl.sound.PlaySoundEvent;
import com.bascenario.engine.scenario.event.impl.sound.StopSoundEvent;
import com.bascenario.engine.scenario.event.impl.sprite.*;
import com.bascenario.engine.scenario.event.impl.sprite.mini.SpriteShakeEvent;
import com.bascenario.render.scenario.ScenarioPreviewScreen;
import com.bascenario.render.scenario.ScenarioScreen;

import java.util.LinkedHashMap;
import java.util.Map;

public class ScenarioRenderTest {
    public static void main(String[] args) {
        Background background = new Background(
                "C:\\Users\\Computer\\BAAS\\Global Assets\\MediaResources\\GameData\\MediaResources\\UIs\\03_Scenario\\01_Background\\BG_GehennaCampus_Night.jpg",
                false, false);

        final int SOUND_1_ID = 0;
        final Sound sound = new Sound("C:\\Users\\Computer\\BAAS\\Named Sound Tracks\\Track_15_Mitsukiyo_Honey_Jam.ogg", 0.5F, 100L, SOUND_1_ID);
        Scenario.Builder scenario = Scenario.builder()
                .name("Hesitant Yet Eager to Share").previewBackground(background).previewSound(sound);

        scenario.add(0, new SetBackgroundEvent(background),
                new PlayDialogueEvent(Dialogue.builder()
                        .name("").association("")
                        .textScale(1).playSpeed(1)
                        .dialogue("I made plans to meet with Hina on Valentine's Day evening.")
                        .closeOnClick(true)
                        .build())
        );
        scenario.add(true, 1, new ShowDialogueOptionEvent(Map.of("\"She said to meet somewhere around here...\"", 0)));
        scenario.add(true, 1, new PlaySoundEvent(
                new Sound("C:\\Users\\Computer\\BAAS\\Named Sound Tracks\\SE_FootStep_02.wav", 1, -1, 1), false
        ));

        final Sprite hinaSprite = new Sprite("C:\\Users\\Computer\\BAAS\\OldSpine-3.8\\Spine_Characters\\hina_spr\\hina_spr.skel", "C:\\Users\\Computer\\BAAS\\OldSpine-3.8\\Spine_Characters\\hina_spr\\hina_spr.atlas", "Idle_01", true);
        final int HINA_SPRITE_ID = 0;

        scenario.add(2000L,
                new PlayDialogueEvent(Dialogue.builder()
                .name("Hina").association("Prefect Team")
                .textScale(1).playSpeed(1)
                .dialogue("Oh... Hello, Sensei.")
                .build()),

                new AddSpriteEvent(hinaSprite, HINA_SPRITE_ID),
                new SpriteAnimationEvent(HINA_SPRITE_ID, "12", 1, true),
                new SpriteLocationEvent(0, HINA_SPRITE_ID, 100, 50),
                new SpriteLocationEvent(HINA_SPRITE_ID, 1000L, 20, 50),
                new QueueEventEvent(800L, new PlayEmoticonEvent(HINA_SPRITE_ID, new Emoticon(
                        2200L, 800 + 300,-390, Emoticon.EmoticonType.SWEAT)))
        );
        scenario.add(true, 1, new ShowDialogueOptionEvent(Map.of("\"Hina!\"", 0)));
        scenario.add(true, 1,
                new SpriteAnimationEvent(HINA_SPRITE_ID, "11", 1, true),
                new SpriteLocationEvent(HINA_SPRITE_ID, 300L, 0, 50),
                new PlayEmoticonEvent(HINA_SPRITE_ID, new Emoticon(800, 800 + 300, -390, Emoticon.EmoticonType.EXCLAMATION_MARK)),
                new PlayDialogueEvent(Dialogue.builder()
                        .name("Hina").association("Prefect Team")
                        .textScale(1).playSpeed(1)
                        .dialogue("D-Don't shout like that!")
                        .build())
        );
        scenario.add(true, 1,
                new SpriteAnimationEvent(HINA_SPRITE_ID, "04", 1, true),
                new PlayDialogueEvent(Dialogue.builder()
                        .name("Hina").association("Prefect Team")
                        .textScale(1).playSpeed(1)
                        .dialogue("Please keep your voice down. I snuck away from duties.\nThe last thing I want is for us to draw unwanted attention.")
                        .build())
        );
        scenario.add(true, 1,
                new SpriteAnimationEvent(HINA_SPRITE_ID, "14", 1, true),
                new PlayEmoticonEvent(HINA_SPRITE_ID, new Emoticon(2200L, 800 + 300,-390, Emoticon.EmoticonType.SWEAT)),
                new SpriteShakeEvent(HINA_SPRITE_ID, 500L, 60L),
                new PlayDialogueEvent(Dialogue.builder()
                        .name("Hina").association("Prefect Team")
                        .textScale(1).playSpeed(1)
                        .dialogue("I can't imagine you do either.")
                        .build())
        );

        {
            final Map<String, Integer> options = new LinkedHashMap<>();
            options.put("\"I have nothing to hide when it comes to you.\"", 0);
            options.put("\"You're worth any hassle that comes my way.\"", 0);

            scenario.add(true, 1, new ShowDialogueOptionEvent(options));
        }

        scenario.add(true, 1,
                new SpriteAnimationEvent(HINA_SPRITE_ID, "04", 1, true),
                new PlayEmoticonEvent(HINA_SPRITE_ID, new Emoticon(1400L, 670 + 300,-390, Emoticon.EmoticonType.ANXIETY)),
                new PlayDialogueEvent(Dialogue.builder()
                        .name("Hina").association("Prefect Team")
                        .textScale(1).playSpeed(1)
                        .dialogue("*sigh* What does someone say to something like that?")
                        .build())
        );

        scenario.add(true, 1,
                new SpriteAnimationEvent(HINA_SPRITE_ID, "16", 1, true),
                new PlayDialogueEvent(Dialogue.builder()
                        .name("Hina").association("Prefect Team")
                        .textScale(1).playSpeed(1)
                        .dialogue("Not that I've come to expect anything less from you.")
                        .build())
        );

        scenario.add(true, 1,
                new SpriteAnimationEvent(HINA_SPRITE_ID, "18", 1, true),
                new PlayEmoticonEvent(HINA_SPRITE_ID, new Emoticon(2000L, 670 + 300,-400, Emoticon.EmoticonType.HESITATED)),
                new PlayDialogueEvent(Dialogue.builder()
                        .name("Hina").association("Prefect Team")
                        .textScale(1).playSpeed(1)
                        .dialogue("Moving on... I'm sorry for contacting you on such short notice,\nbut I'm grateful you made the time to come see me.")
                        .build())
        );

        scenario.add(true, 1,
                new SpriteShakeEvent(HINA_SPRITE_ID, 500L, 60L),
                new SpriteAnimationEvent(HINA_SPRITE_ID, "12", 1, true),
                new PlayDialogueEvent(Dialogue.builder()
                        .name("Hina").association("Prefect Team")
                        .textScale(1).playSpeed(1)
                        .dialogue("So...")
                        .build())
        );

        scenario.add(true, 1,
                new SpriteLocationEvent(HINA_SPRITE_ID, 300L, 0, 60),
                new QueueEventEvent(300L, new SpriteLocationEvent(HINA_SPRITE_ID, 300L, 0, 50)),
                new PlayEmoticonEvent(HINA_SPRITE_ID, new Emoticon(2000L, 670 + 300,-400, Emoticon.EmoticonType.SHY)),
                new SpriteAnimationEvent(HINA_SPRITE_ID, "17", 1, true),
                new PlayDialogueEvent(Dialogue.builder()
                        .name("Hina").association("Prefect Team")
                        .textScale(1).playSpeed(1)
                        .dialogue("Thank you.").closeOnClick(true)
                        .build())
        );

        scenario.add(true, 1, new ShowDialogueOptionEvent(Map.of("\"Sure, but if you don't want us to be seen, does that mean...\"", 0)));

        scenario.add(true, 1,
                new PlaySoundEvent(new Sound("C:\\Users\\Computer\\BAAS\\Named Sound Tracks\\SE_FootStep_02.wav", 1,-1, 1), false),
                new StopSoundEvent(SOUND_1_ID, 1000L)
        );

        scenario.add(2200L,
                new SpriteAnimationEvent(HINA_SPRITE_ID, "11", 1, true),
                new SpriteScaleEvent(HINA_SPRITE_ID, 0, 1.32F),
                new SpriteLocationEvent(HINA_SPRITE_ID, 0, 0, 80),
                new SpriteShakeEvent(HINA_SPRITE_ID, 500L, 60L),
                new PlayEmoticonEvent(HINA_SPRITE_ID, new Emoticon(800, 700, -700, Emoticon.EmoticonType.SURPRISED)),
                new PlayDialogueEvent(Dialogue.builder()
                        .name("Hina").association("Prefect Team")
                        .textScale(1).playSpeed(1)
                        .dialogue("Wh-What are you getting at?!")
                        .build())
        );

        {
            final Map<String, Integer> options = new LinkedHashMap<>();
            options.put("\"(What's really going on, Hina?)\"", 0);
            options.put("\"(Is this some kind of secret meeting?)\"", 0);

            scenario.add(true, 1, new ShowDialogueOptionEvent(options));
        }

        scenario.add(true, 1,
                new SpriteAnimationEvent(HINA_SPRITE_ID, "10", 1, true),
                new PlayDialogueEvent(Dialogue.builder()
                        .name("Hina").association("Prefect Team")
                        .textScale(1).playSpeed(1)
                        .dialogue("I told you to lower your voice, but you don't have to whisper in my ear!")
                        .build())
        );

        scenario.add(true, 1,
                new SpriteAnimationEvent(HINA_SPRITE_ID, "11", 1, true),
                new SpriteLocationEvent(HINA_SPRITE_ID, 0, 0, 50),
                new QueueEventEvent(100L, new SpriteLocationEvent(HINA_SPRITE_ID, 300L, 0, 40)),
                new QueueEventEvent(300L, new SpriteLocationEvent(HINA_SPRITE_ID, 300L, 0, 50)),
                new SpriteScaleEvent(HINA_SPRITE_ID, 0, 1),
                new PlayEmoticonEvent(HINA_SPRITE_ID, new Emoticon(800, 800 + 300, -390, Emoticon.EmoticonType.EXCLAMATION_MARK)),
                new PlayDialogueEvent(Dialogue.builder()
                        .name("Hina").association("Prefect Team")
                        .textScale(1).playSpeed(1)
                        .dialogue("B-Back up!")
                        .build())
        );

        scenario.add(true, 1, new ShowDialogueOptionEvent(Map.of("\"What's wrong?\"", 0)));

        scenario.add(true, 1,
                new SpriteAnimationEvent(HINA_SPRITE_ID, "18", 1, true),
                new PlayEmoticonEvent(HINA_SPRITE_ID, new Emoticon(2200L, 800 + 300,-390, Emoticon.EmoticonType.SWEAT)),
                new PlayDialogueEvent(Dialogue.builder()
                        .name("Hina").association("Prefect Team")
                        .textScale(1).playSpeed(1)
                        .dialogue("Let's both just...calm down first.")
                        .build())
        );

        scenario.add(true, 1,
                new SpriteAnimationEvent(HINA_SPRITE_ID, "13", 1, false),
                new SpriteShakeEvent(HINA_SPRITE_ID, 500L, 60L),
                new PlayDialogueEvent(Dialogue.builder()
                        .name("Hina").association("Prefect Team")
                        .textScale(0.87F).playSpeed(1)
                        .dialogue("Actually, maybe I'm the only one who needs to.")
                        .build())
        );

        scenario.add(true, 1,
                new SpriteAnimationEvent(HINA_SPRITE_ID, "20", 1, false),
                new PlaySoundEvent(new Sound("C:\\Users\\Computer\\BAAS\\Named Sound Tracks\\SFX_Emoticon_Motion_Surprise.wav", 1,-1, 1), false),
                new QueueEventEvent(100L, new SpriteLocationEvent(HINA_SPRITE_ID, 300L, 0, 60)),
                new QueueEventEvent(300L, new SpriteLocationEvent(HINA_SPRITE_ID, 300L, 0, 50)),
                new PlayDialogueEvent(Dialogue.builder()
                        .name("Hina").association("Prefect Team")
                        .textScale(1).playSpeed(1)
                        .dialogue("R-Regardless! I asked you here tonight because of this.").closeOnClick(true)
                        .build())
        );

        scenario.add(true, 1,
                new SpriteFadeEvent(HINA_SPRITE_ID, 300L, 1),
                new QueueEventEvent(300L, new SpriteScaleEvent(HINA_SPRITE_ID, 0, 0)),
                new QueueEventEvent(300L, new PlaySoundEvent(new Sound("C:\\Users\\Computer\\BAAS\\Named Sound Tracks\\SE_Gear_02.wav", 1,-1, 1), false)),
                new QueueEventEvent(400, new SetPopupEvent(new PopupImage(
                        "C:\\Users\\Computer\\BAAS\\Global Assets\\MediaResources\\GameData\\MediaResources\\UIs\\03_Scenario\\04_ScenarioImage\\Event01_Hina.PNG", 3000L)))
        );

        scenario.add(3500L, new ShowDialogueOptionEvent(Map.of("\"Is this what I think it is?\"", 0)));

        final Sound sound1 = new Sound("C:\\Users\\Computer\\BAAS\\Named Sound Tracks\\Track_46_Nor_Sugar_story.ogg", 0.5F, 100L, 2);
        scenario.add(true, 1,
                new PlaySoundEvent(sound1, true),
                new SpriteScaleEvent(HINA_SPRITE_ID, 0, 1),
                new SpriteFadeEvent(HINA_SPRITE_ID, 300L, 0),
                new SpriteAnimationEvent(HINA_SPRITE_ID, "12", 1, true),
                new PlayDialogueEvent(Dialogue.builder()
                        .name("Hina").association("Prefect Team")
                        .textScale(1).playSpeed(1)
                        .dialogue("Since it's Valentine's Day, I tried my hand at...making some myself.")
                        .build())
        );

        scenario.add(true, 1,
                new SpriteAnimationEvent(HINA_SPRITE_ID, "13", 1, true),
                new PlayDialogueEvent(Dialogue.builder()
                        .name("Hina").association("Prefect Team")
                        .textScale(1).playSpeed(1)
                        .dialogue("I know it doesn't look very appetizing.")
                        .build())
        );

        scenario.add(true, 1,
                new SpriteLocationEvent(HINA_SPRITE_ID, 300L, 0, 60),
                new QueueEventEvent(300L, new SpriteLocationEvent(HINA_SPRITE_ID, 300L, 0, 50)),
                new PlayEmoticonEvent(HINA_SPRITE_ID, new Emoticon(2000L, 670 + 300,-400, Emoticon.EmoticonType.ANXIETY)),
                new SpriteAnimationEvent(HINA_SPRITE_ID, "14", 1, true),
                new PlayDialogueEvent(Dialogue.builder()
                        .name("Hina").association("Prefect Team")
                        .textScale(1).playSpeed(1)
                        .dialogue("It was the best I could do after several tries.")
                        .build())
        );

        scenario.add(true, 1,
                new SpriteAnimationEvent(HINA_SPRITE_ID, "99", 1, true),
                new PlayDialogueEvent(Dialogue.builder()
                        .name("Hina").association("Prefect Team")
                        .textScale(1).playSpeed(1)
                        .dialogue("Maybe it's fitting that it's ugly and anything but cutesy on the outside.")
                        .build())
        );

        scenario.add(true, 1,
                new PlayEmoticonEvent(HINA_SPRITE_ID, new Emoticon(2000L, 670 + 300,-400, Emoticon.EmoticonType.HESITATED)),
                new SpriteAnimationEvent(HINA_SPRITE_ID, "12", 1, true),
                new PlayDialogueEvent(Dialogue.builder()
                        .name("Hina").association("Prefect Team")
                        .textScale(1).playSpeed(1)
                        .dialogue("But under the surface, it should still...")
                        .build())
        );

        scenario.add(true, 1,
                new SpriteAnimationEvent(HINA_SPRITE_ID, "18", 1, true),
                new PlayDialogueEvent(Dialogue.builder()
                        .name("Hina").association("Prefect Team")
                        .textScale(1).playSpeed(1)
                        .dialogue("...taste like any other chocolate.")
                        .build())
        );

        scenario.add(true, 1,
                new SpriteShakeEvent(HINA_SPRITE_ID, 500L, 60L),
                new PlayEmoticonEvent(HINA_SPRITE_ID, new Emoticon(2000L, 670 + 300,-400, Emoticon.EmoticonType.SHY)),
                new SpriteAnimationEvent(HINA_SPRITE_ID, "19", 1, true),
                new PlayDialogueEvent(Dialogue.builder()
                        .name("Hina").association("Prefect Team")
                        .textScale(1).playSpeed(1)
                        .dialogue("S-So if you wouldn't mind accepting it, then I...")
                        .build())
        );

        scenario.add(true, 1, new ShowDialogueOptionEvent(Map.of("\"Homemade chocolate from you, Hina? I'm honored!\"", 0)));

        scenario.add(true, 1,
                new SpriteAnimationEvent(HINA_SPRITE_ID, "11", 1, true),
                new PlayEmoticonEvent(HINA_SPRITE_ID, new Emoticon(2200L, 800 + 300,-390, Emoticon.EmoticonType.SURPRISED)),
                new SpriteShakeEvent(HINA_SPRITE_ID, 500L, 60L),
                new PlayDialogueEvent(Dialogue.builder()
                        .name("Hina").association("Prefect Team")
                        .textScale(1).playSpeed(1)
                        .dialogue("I-It's not that big of a deal, Sensei! You don't have to sound so...")
                        .build())
        );

        scenario.add(true, 1, new ShowDialogueOptionEvent(Map.of("\"I actually got homemade chocolate from Hina! I'll cherish it!\"", 0)));

        scenario.add(true, 1,
                new SpriteLocationEvent(HINA_SPRITE_ID, 300L, 0, 40),
                new QueueEventEvent(300L, new SpriteLocationEvent(HINA_SPRITE_ID, 300L, 0, 50)),
                new PlayEmoticonEvent(HINA_SPRITE_ID, new Emoticon(800, 800 + 300, -390, Emoticon.EmoticonType.EXCLAMATION_MARK)),
                new PlayDialogueEvent(Dialogue.builder()
                        .name("Hina").association("Prefect Team")
                        .textScale(1).playSpeed(1)
                        .dialogue("You, fool! Chocolate is meant to be eaten, not guarded after like some kind of antique.")
                        .build())
        );

        scenario.add(true, 1,
                new SpriteAnimationEvent(HINA_SPRITE_ID, "18", 1, true),
                new SpriteShakeEvent(HINA_SPRITE_ID, 500L, 60L),
                new PlayDialogueEvent(Dialogue.builder()
                        .name("Hina").association("Prefect Team")
                        .textScale(1).playSpeed(1)
                        .dialogue("*sigh* Seriously...")
                        .build())
        );

        scenario.add(true, 1,
                new SpriteAnimationEvent(HINA_SPRITE_ID, "14", 1, true),
                new PlayDialogueEvent(Dialogue.builder()
                        .name("Hina").association("Prefect Team")
                        .textScale(1).playSpeed(1)
                        .dialogue("Heh...")
                        .build())
        );

        scenario.add(true, 1,
                new SpriteAnimationEvent(HINA_SPRITE_ID, "03", 1, true),
                new SpriteLocationEvent(HINA_SPRITE_ID, 300L, 0, 60),
                new QueueEventEvent(300L, new SpriteLocationEvent(HINA_SPRITE_ID, 300L, 0, 50)),
                new PlayDialogueEvent(Dialogue.builder()
                        .name("Hina").association("Prefect Team")
                        .textScale(1).playSpeed(1)
                        .dialogue("Of course that's what you'd say. Why was I so worried?")
                        .build())
        );

        scenario.add(true, 1,
                new PlayDialogueEvent(Dialogue.builder()
                        .name("Hina").association("Prefect Team")
                        .textScale(1).playSpeed(1)
                        .dialogue("Thank you, Sensei. I hope you enjoy it.")
                        .build())
        );

        scenario.add(true, 1,
                new SpriteAnimationEvent(HINA_SPRITE_ID, "14", 1, true),
                new PlayEmoticonEvent(HINA_SPRITE_ID, new Emoticon(2000L, 670 + 300,-400, Emoticon.EmoticonType.SHY)),
                new PlayDialogueEvent(Dialogue.builder()
                        .name("Hina").association("Prefect Team")
                        .textScale(1).playSpeed(1)
                        .dialogue("I... put a lot of effort into it.")
                        .build())
        );

        scenario.add(true, 1, new ShowDialogueOptionEvent(Map.of("\"No, thank YOU, Hina!\"", 0)));

        scenario.add(true, 1,
                new PlayEmoticonEvent(HINA_SPRITE_ID, new Emoticon(2000L, 670 + 300,-400, Emoticon.EmoticonType.HESITATED)),
                new SpriteAnimationEvent(HINA_SPRITE_ID, "16", 1, true),
                new PlayDialogueEvent(Dialogue.builder()
                        .name("Hina").association("Prefect Team")
                        .textScale(1).playSpeed(1)
                        .dialogue("Do be sure to eat it, though. I don't want to catch you keeping it like some kind of family heirloom.")
                        .build())
        );

        scenario.add(true, 1, new ShowDialogueOptionEvent(Map.of("\"I'll see what I can do.\"", 0)));

        scenario.add(true, 1,
                new SpriteAnimationEvent(HINA_SPRITE_ID, "14", 1, true),
                new PlayEmoticonEvent(HINA_SPRITE_ID, new Emoticon(800, 800 + 300, -390, Emoticon.EmoticonType.QUESTION_MARK)),
                new PlayDialogueEvent(Dialogue.builder()
                        .name("Hina").association("Prefect Team")
                        .textScale(1).playSpeed(1)
                        .dialogue("I'm not sure what that supposed to mean...")
                        .build())
        );

        scenario.add(true, 1,
                new SpriteAnimationEvent(HINA_SPRITE_ID, "12", 1, true),
                new SpriteShakeEvent(HINA_SPRITE_ID, 500, 60),
                new PlayDialogueEvent(Dialogue.builder()
                        .name("Hina").association("Prefect Team")
                        .textScale(1).playSpeed(1)
                        .dialogue("Well, as long as you enjoyed it, I supposed it all the same to me.")
                        .build())
        );

        scenario.add(true, 1,
                new SpriteAnimationEvent(HINA_SPRITE_ID, "17", 1, true),
                new PlayDialogueEvent(Dialogue.builder()
                        .name("Hina").association("Prefect Team")
                        .textScale(1).playSpeed(1)
                        .dialogue("Heehee.")
                        .build())
        );

        boolean fullScreen = args.length > 1 && args[1].equals("fullscreen") || args.length > 0 && args[0].equals("fullscreen");
        if (args.length > 0 && args[0].equals("skip-preview")) {
            Launcher.launch(new ScenarioScreen(scenario.build()), fullScreen);
        } else {
            Launcher.launch(new ScenarioPreviewScreen(scenario.build()), fullScreen);
        }
    }
}

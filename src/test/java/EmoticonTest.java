import com.bascenario.Launcher;
import com.bascenario.engine.scenario.Scenario;
import com.bascenario.engine.scenario.elements.*;
import com.bascenario.engine.scenario.event.impl.*;
import com.bascenario.engine.scenario.event.impl.background.SetBackgroundEvent;
import com.bascenario.engine.scenario.event.impl.sprite.AddSpriteEvent;
import com.bascenario.engine.scenario.event.impl.sprite.SpriteAnimationEvent;
import com.bascenario.engine.scenario.event.impl.sprite.SpriteLocationEvent;
import com.bascenario.render.scenario.ScenarioPreviewScreen;
import com.bascenario.render.scenario.ScenarioScreen;

public class EmoticonTest {
    public static void main(String[] args) {
        Background background = new Background(
                "C:\\Users\\PC\\Downloads\\global\\MediaResources\\GameData\\MediaResources\\UIs\\03_Scenario\\01_Background\\BG_GehennaCampus_Night.jpg",
                false, false);

        Scenario scenario = Scenario.builder()
                .name("Emoticon Test").previewBackground(background)
                .build();

        final Sprite hinaSprite = new Sprite("C:\\Users\\PC\\Downloads\\CH0230_spr.skel", "C:\\Users\\PC\\Downloads\\CH0230_spr.atlas", "Idle_01", true);
        final Sprite hoshinoSprite = new Sprite("C:\\Users\\PC\\Downloads\\hoshino_swimsuit_spr.skel",
                "C:\\Users\\PC\\Downloads\\hoshino_swimsuit_spr.atlas", "Idle_01", true);

        final int HINA_SPRITE_ID = 0, HOSHINO_SPRITE_ID = 1;
        scenario.add(0,
                new SetBackgroundEvent(background),
                new AddSpriteEvent(hinaSprite, HINA_SPRITE_ID),
                new AddSpriteEvent(hoshinoSprite, HOSHINO_SPRITE_ID),
                new SpriteLocationEvent(HINA_SPRITE_ID, 0, -30, 50),
                new SpriteLocationEvent(HOSHINO_SPRITE_ID, 0, 30, 50),
                new SpriteAnimationEvent(HOSHINO_SPRITE_ID, "11", 1, true)
        );

        for (Sprite.EmoticonType type : Sprite.EmoticonType.values()) {
            addEmoticon(scenario, type, HINA_SPRITE_ID, HOSHINO_SPRITE_ID);
        }

        boolean fullScreen = args.length > 1 && args[1].equals("fullscreen") || args.length > 0 && args[0].equals("fullscreen");
        if (args.length > 0 && args[0].equals("skip-preview")) {
            Launcher.launch(new ScenarioScreen(scenario), fullScreen);
        } else {
            Launcher.launch(new ScenarioPreviewScreen(scenario), fullScreen);
        }
    }

    private static void addEmoticon(final Scenario scenario, Sprite.EmoticonType type, final int... sprite) {
        scenario.add(type.ordinal() == 0 ? 0 : 1200L,
                new PlayEmoticonEvent(sprite[0], new Sprite.Emoticon(1200L, 670 + 300, -390, type)),
                new PlayEmoticonEvent(sprite[1], new Sprite.Emoticon(1200L, 670 + 300, -390, type))
        );
    }
}

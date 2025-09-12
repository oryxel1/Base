import com.bascenario.engine.scenario.Scenario;
import com.bascenario.engine.scenario.elements.Background;
import com.bascenario.engine.scenario.elements.Sprite;
import com.bascenario.engine.scenario.event.impl.PlayEmoticonEvent;
import com.bascenario.engine.scenario.event.impl.SetBackgroundEvent;
import com.bascenario.engine.scenario.event.impl.sprite.AddSpriteEvent;
import com.bascenario.engine.scenario.event.impl.sprite.SpriteLocationEvent;
import com.bascenario.engine.scenario.event.impl.sprite.SpriteScaleEvent;
import com.bascenario.engine.scenario.screen.ScenarioScreen;
import com.bascenario.launcher.Launcher;

public class EmoticonTest2 {
    public static void main(String[] args) {
        Background background = new Background(
                "C:\\Users\\PC\\Downloads\\global\\MediaResources\\GameData\\MediaResources\\UIs\\03_Scenario\\01_Background\\BG_GehennaCampus_Night.jpg",
                false, false);

        Scenario scenario = Scenario.builder()
                .name("Emoticon Test").previewBackground(background)
                .build();

        final Sprite hinaSprite = new Sprite("C:\\Users\\PC\\Downloads\\hina_spr.skel", "C:\\Users\\PC\\Downloads\\hina_spr.atlas", "Idle_01", true);
        scenario.add(0,
                new SetBackgroundEvent(background),
                new AddSpriteEvent(hinaSprite),
                new SpriteScaleEvent(0, hinaSprite, 1.32F),
                new SpriteLocationEvent(0, hinaSprite, 0, 80)
        );

        for (Sprite.EmoticonType type : Sprite.EmoticonType.values()) {
            addEmoticon(scenario, type, hinaSprite);
        }

        Launcher.launch(new ScenarioScreen(scenario), false);
    }

    private static void addEmoticon(final Scenario scenario, Sprite.EmoticonType type, final Sprite... sprite) {
        scenario.add(type.ordinal() == 0 ? 0 : 1200L,
                new PlayEmoticonEvent(sprite[0], new Sprite.Emoticon(1200L, 800 / 1.32F, -390 * 1.32F, type))
        );
    }
}

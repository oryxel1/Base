import com.bascenario.engine.scenario.Scenario;
import com.bascenario.engine.scenario.elements.*;
import com.bascenario.engine.scenario.event.impl.*;
import com.bascenario.engine.scenario.event.impl.sprite.AddSpriteEvent;
import com.bascenario.engine.scenario.event.impl.sprite.SpriteLocationEvent;
import com.bascenario.engine.scenario.screen.ScenarioPreviewScreen;
import com.bascenario.engine.scenario.screen.ScenarioScreen;
import com.bascenario.launcher.Launcher;

public class EmoticonTest {
    public static void main(String[] args) {
        Background background = new Background(
                "C:\\Users\\PC\\Downloads\\output\\MediaResources\\GameData\\MediaResources\\UIs\\03_Scenario\\01_Background\\BG_GehennaCampus_Night.jpg",
                false, false);

        Scenario scenario = Scenario.builder()
                .name("Emoticon Test").previewBackground(background)
                .build();

        final Sprite hinaSprite = new Sprite("C:\\Users\\PC\\Downloads\\hina_spr.skel", "C:\\Users\\PC\\Downloads\\hina_spr.atlas", "Idle_01", true);
        scenario.add(0,
                new SetBackgroundEvent(background),
                new AddSpriteEvent(hinaSprite),
                new SpriteLocationEvent(0, hinaSprite, 0, 50),
                new PlayEmoticonEvent(hinaSprite, new Sprite.Emoticon(600L, 800, -360, Sprite.EmoticonType.SWEAT))
        );

        boolean fullScreen = args.length > 1 && args[1].equals("fullscreen") || args.length > 0 && args[0].equals("fullscreen");
        if (args.length > 0 && args[0].equals("skip-preview")) {
            Launcher.launch(new ScenarioScreen(scenario), fullScreen);
        } else {
            Launcher.launch(new ScenarioPreviewScreen(scenario), fullScreen);
        }
    }
}

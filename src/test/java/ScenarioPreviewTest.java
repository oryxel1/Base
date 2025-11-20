import oxy.bascenario.api.Scenario;
import oxy.bascenario.api.effects.Fade;
import oxy.bascenario.api.elements.Sprite;
import oxy.bascenario.api.elements.image.FadeImage;
import oxy.bascenario.api.event.impl.ColorOverlayEvent;
import oxy.bascenario.api.event.impl.SetBackgroundEvent;
import oxy.bascenario.api.event.impl.element.AddElementEvent;
import oxy.bascenario.api.event.impl.element.MoveElementEvent;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.api.utils.FileInfo;
import oxy.bascenario.screens.ScenarioPreviewScreen;

import java.awt.*;

import static oxy.bascenario.Launcher.*;

public class ScenarioPreviewTest {
    public static void main(String[] args) {
        final Scenario.Builder builder = Scenario.builder();
        builder.title("Round 2: Jet Boat Rampage");
        builder.subtitle("Episode 3");
//        builder.previewBackground(new Image());

        builder.add(0, new SetBackgroundEvent(new FadeImage(FileInfo.from("C:\\Users\\Computer\\BAAS\\Temporary\\BG_MainOffice_Night.jpg"), new Fade(1000), Fade.DISABLED)));
        builder.add(1000, new ColorOverlayEvent(500, new Fade(100), new Fade(500), Color.WHITE));

        final Sprite sprite = new Sprite(FileInfo.from("C:\\Users\\Computer\\BAAS\\JPSpine\\CH0326_spr.skel"), FileInfo.from("C:\\Users\\Computer\\BAAS\\JPSpine\\CH0326_spr.atlas"), null);
        builder.add(0, new AddElementEvent(0, sprite, RenderLayer.BEHIND_DIALOGUE), new MoveElementEvent(0, 0, 540, MoveElementEvent.Type.Y_ONLY));

        builder.add(1000, new ColorOverlayEvent(500, new Fade(100), new Fade(500), Color.WHITE));

        launch(new ScenarioPreviewScreen(builder.build()), false);
    }
}

import net.lenni0451.commons.color.Color;
import oxy.bascenario.api.Scenario;
import oxy.bascenario.api.effects.Fade;
import oxy.bascenario.api.elements.Sprite;
import oxy.bascenario.api.elements.image.FadeImage;
import oxy.bascenario.api.elements.image.Image;
import oxy.bascenario.api.event.impl.ColorOverlayEvent;
import oxy.bascenario.api.event.impl.SetBackgroundEvent;
import oxy.bascenario.api.event.impl.element.AddElementEvent;
import oxy.bascenario.api.event.impl.element.MoveElementEvent;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.api.utils.FileInfo;
import oxy.bascenario.screens.ScenarioPreviewScreen;
import oxy.bascenario.screens.ScenarioScreen;

import static oxy.bascenario.Launcher.*;

public class ScenarioPreviewTest {
    public static void main(String[] args) {
        final Scenario.Builder builder = Scenario.builder();
        builder.title("Title");
        builder.subtitle("Subtitle");
        builder.previewBackground(new Image(FileInfo.from("C:\\Users\\Computer\\BAAS\\Temporary\\BG_MainOffice_Night.jpg")));

        builder.add(0, new SetBackgroundEvent(new FadeImage(FileInfo.from("C:\\Users\\Computer\\BAAS\\Temporary\\BG_MainOffice_Night.jpg"), Fade.DISABLED, Fade.DISABLED)));
//        builder.add(1000, new ColorOverlayEvent(500, new Fade(100), new Fade(500), Color.WHITE));

        final Sprite sprite = new Sprite(FileInfo.from("C:\\Users\\Computer\\BAAS\\JPSpine\\CH0326_spr.skel"), FileInfo.from("C:\\Users\\Computer\\BAAS\\JPSpine\\CH0326_spr.atlas"), null);
        builder.add(0, new AddElementEvent(0, sprite, RenderLayer.BEHIND_DIALOGUE), new MoveElementEvent(0, 0, 960, 540));

//        builder.add(1000, new ColorOverlayEvent(new Fade(100), Color.WHITE));
//        builder.add(120, new ColorOverlayEvent(new Fade(500), Color.fromRGBA(255, 255, 255, 0)));
        builder.add(1000, new ColorOverlayEvent(0, Fade.DISABLED, Color.fromRGBA(0, 0, 0, 0)));
        builder.add(0, new MoveElementEvent(500, 0, 560, MoveElementEvent.Type.X_ONLY), new ColorOverlayEvent(0, new Fade(1000), Color.fromRGBA(0, 0, 0, 100)));

        launch(new ScenarioScreen(builder.build()), false);
//        launch(new ScenarioPreviewScreen(builder.build()), true);
    }
}

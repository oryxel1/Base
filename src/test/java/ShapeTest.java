import net.lenni0451.commons.color.Color;
import oxy.bascenario.api.Scenario;
import oxy.bascenario.api.effects.Fade;
import oxy.bascenario.api.elements.Sprite;
import oxy.bascenario.api.elements.effect.OverlayEffect;
import oxy.bascenario.api.elements.image.FadeImage;
import oxy.bascenario.api.elements.image.Image;
import oxy.bascenario.api.elements.shape.Circle;
import oxy.bascenario.api.elements.shape.Rectangle;
import oxy.bascenario.api.event.impl.SetBackgroundEvent;
import oxy.bascenario.api.event.impl.SpriteAnimationEvent;
import oxy.bascenario.api.event.impl.element.AddElementEvent;
import oxy.bascenario.api.event.impl.element.MoveElementEvent;
import oxy.bascenario.api.event.impl.element.effect.OverlayEffectEvent;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.api.utils.FileInfo;
import oxy.bascenario.screens.ScenarioScreen;

import static oxy.bascenario.Launcher.launch;

public class ShapeTest {
    public static void main(String[] args) {
        final Scenario.Builder builder = Scenario.builder();
        builder.title("Shape Test");
        builder.subtitle("Test");
        builder.previewBackground(new Image(FileInfo.from("C:\\Users\\Computer\\BAAS\\Temporary\\BG_MainOffice_Night.jpg")));

//        builder.add(0, new SetBackgroundEvent(new FadeImage(FileInfo.from("C:\\Users\\Computer\\BAAS\\Temporary\\BG_MainOffice_Night.jpg"), Fade.DISABLED, Fade.DISABLED)));
        builder.add(0, new AddElementEvent(0, new Rectangle(1920, 1080, Color.WHITE, false), RenderLayer.BEHIND_DIALOGUE), new MoveElementEvent(0, 0, 0, 0));
        builder.add(0, new OverlayEffectEvent(0, OverlayEffect.RAINBOW_Y, OverlayEffectEvent.Type.ADD));
        builder.add(0, new AddElementEvent(1, new Circle(100, Color.WHITE, false), RenderLayer.BEHIND_DIALOGUE), new MoveElementEvent(0, 1, 800, 800));
        builder.add(0, new OverlayEffectEvent(1, OverlayEffect.HOLOGRAM_Y, OverlayEffectEvent.Type.ADD));
        builder.add(0, new OverlayEffectEvent(0, OverlayEffect.BLUR, OverlayEffectEvent.Type.ADD));

        launch(new ScenarioScreen(builder.build()), false);
    }
}

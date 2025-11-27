import oxy.bascenario.api.Scenario;
import oxy.bascenario.api.effects.Fade;
import oxy.bascenario.api.elements.effect.OverlayEffect;
import oxy.bascenario.api.elements.Sprite;
import oxy.bascenario.api.elements.image.FadeImage;
import oxy.bascenario.api.elements.image.Image;
import oxy.bascenario.api.event.impl.SetBackgroundEvent;
import oxy.bascenario.api.event.impl.SpriteAnimationEvent;
import oxy.bascenario.api.event.impl.element.AddElementEvent;
import oxy.bascenario.api.event.impl.element.MoveElementEvent;
import oxy.bascenario.api.event.impl.element.effect.OverlayEffectEvent;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.api.utils.FileInfo;
import oxy.bascenario.screens.ScenarioScreen;

import static oxy.bascenario.Launcher.launch;

public class HologramTest {
    public static void main(String[] args) {
        final Scenario.Builder builder = Scenario.builder();
        builder.title("Hologram Test");
        builder.subtitle("Test");
        builder.previewBackground(new Image(FileInfo.from("C:\\Users\\Computer\\BAAS\\Temporary\\BG_MainOffice_Night.jpg")));

        builder.add(0, new SetBackgroundEvent(new FadeImage(FileInfo.from("C:\\Users\\Computer\\BAAS\\Temporary\\BG_MainOffice_Night.jpg"), Fade.DISABLED, Fade.DISABLED)));
        final Sprite sprite = new Sprite(FileInfo.from("C:\\Users\\Computer\\BAAS\\JPSpine\\CH0326_spr.skel"), FileInfo.from("C:\\Users\\Computer\\BAAS\\JPSpine\\CH0326_spr.atlas"), null);
        builder.add(0, new AddElementEvent(0, sprite, RenderLayer.BEHIND_DIALOGUE),
                new SpriteAnimationEvent(0, 0,  "Idle_01", 0),
                new MoveElementEvent(0, 0, 960, 540));
        builder.add(0, new OverlayEffectEvent(0, OverlayEffect.RAINBOW_Y, OverlayEffectEvent.Type.ADD), new OverlayEffectEvent(0, OverlayEffect.RAINBOW_X, OverlayEffectEvent.Type.ADD), new OverlayEffectEvent(0, OverlayEffect.HOLOGRAM_Y, OverlayEffectEvent.Type.ADD), new OverlayEffectEvent(0, OverlayEffect.HOLOGRAM_X, OverlayEffectEvent.Type.ADD));

        builder.add(1000, new OverlayEffectEvent(0, OverlayEffect.RAINBOW_Y, OverlayEffectEvent.Type.REMOVE), new OverlayEffectEvent(0, OverlayEffect.RAINBOW_X, OverlayEffectEvent.Type.REMOVE));

        launch(new ScenarioScreen(builder.build()), false);
    }
}

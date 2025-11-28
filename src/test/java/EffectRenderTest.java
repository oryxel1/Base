import net.lenni0451.commons.color.Color;
import oxy.bascenario.Launcher;
import oxy.bascenario.api.Scenario;
import oxy.bascenario.api.effects.Easing;
import oxy.bascenario.api.effects.Effect;
import oxy.bascenario.api.event.element.AddElementEvent;
import oxy.bascenario.api.event.element.ElementEffectEvent;
import oxy.bascenario.api.event.element.values.PositionElementEvent;
import oxy.bascenario.api.event.element.values.RotateElementEvent;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.api.render.elements.RendererImage;
import oxy.bascenario.api.render.elements.Sprite;
import oxy.bascenario.api.render.elements.image.Image;
import oxy.bascenario.api.render.elements.shape.Rectangle;
import oxy.bascenario.api.render.elements.text.Text;
import oxy.bascenario.api.render.elements.text.TextSegment;
import oxy.bascenario.api.utils.FileInfo;
import oxy.bascenario.api.utils.math.Axis;
import oxy.bascenario.api.utils.math.Vec2;
import oxy.bascenario.screens.ScenarioScreen;

import java.util.List;

public class EffectRenderTest {
    public static void main(String[] args) {
        final Scenario.Builder scenario = new Scenario.Builder();
        scenario.title("Effect Test.");

        final Sprite sprite = new Sprite(new FileInfo("CH0326_spr.skel", false, true), new FileInfo("CH0326_spr.atlas", false, true));

        final Text text = new Text(List.of(TextSegment.builder().text("A really simple text.").build()), 42);

        scenario.add(0,
                new AddElementEvent(-1, new Rectangle(1920, 1080, Color.WHITE, false), RenderLayer.BEHIND_DIALOGUE),
                new AddElementEvent(0, sprite, RenderLayer.BEHIND_DIALOGUE),
                new AddElementEvent(1, text, RenderLayer.TOP),
                new AddElementEvent(2, new RendererImage(new Image(new FileInfo("potato.jpg", false, true)), Color.WHITE, 200, 200), RenderLayer.TOP),
                new AddElementEvent(3, sprite, RenderLayer.BEHIND_DIALOGUE)
        );

        scenario.add(0,
                new PositionElementEvent(0, 0, new Vec2(960, 540), Easing.LINEAR, PositionElementEvent.Type.POSITION),
                new PositionElementEvent(1, 0, new Vec2(50, 50), Easing.LINEAR, PositionElementEvent.Type.POSITION),
                new PositionElementEvent(2, 0, new Vec2(50, 500), Easing.LINEAR, PositionElementEvent.Type.POSITION),
                new PositionElementEvent(3, 0, new Vec2(960, 540), Easing.LINEAR, PositionElementEvent.Type.POSITION)
        );

        scenario.add(0,
                new ElementEffectEvent(0, Effect.HOLOGRAM, Axis.Y),
                new ElementEffectEvent(1, Effect.RAINBOW, Axis.X, 1.5f),
                new ElementEffectEvent(2, Effect.BLUR, 5),
                new ElementEffectEvent(3, Effect.OUTLINE, 1, 1 << 1)
        );

        Launcher.launch(new ScenarioScreen(scenario.build()), false);
    }
}

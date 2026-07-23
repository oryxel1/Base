package scenario;

import net.lenni0451.commons.color.Color;
import oxy.base.api.render.elements.image.Image;
import oxy.base.utils.Launcher;
import oxy.base.api.Scenario;
import oxy.base.api.effects.Easing;
import oxy.base.api.effects.Effect;
import oxy.base.api.event.element.AddElementEvent;
import oxy.base.api.event.element.ElementEffectEvent;
import oxy.base.api.event.element.values.PositionElementEvent;
import oxy.base.api.render.RenderLayer;
import oxy.base.api.render.elements.Sprite;
import oxy.base.api.render.elements.shape.Rectangle;
import oxy.base.api.render.elements.text.Text;
import oxy.base.api.render.elements.text.TextSegment;
import oxy.base.api.utils.FileInfo;
import oxy.base.api.utils.math.Axis;
import oxy.base.api.utils.math.Vec2;
import oxy.base.screens.ScenarioScreen;

import java.util.List;

public class EffectRenderTest {
    public static void main(String[] args) {
        final Scenario.Builder scenario = new Scenario.Builder();

        final Sprite sprite = new Sprite(new FileInfo("CH0326_spr.skel", false, true), new FileInfo("CH0326_spr.atlas", false, true));

        final Text text = new Text(List.of(TextSegment.builder().text("A really simple text.").build()), 42);

        scenario.add(0,
                new AddElementEvent(0, new Rectangle(1920, 1080, Color.WHITE, false), RenderLayer.BEHIND_DIALOGUE),
                new AddElementEvent(1, sprite, RenderLayer.BEHIND_DIALOGUE),
                new AddElementEvent(2, text, RenderLayer.TOP),
                new AddElementEvent(3, new Image(new FileInfo("potato.jpg", false, true), Color.WHITE, 200, 200), RenderLayer.TOP),
                new AddElementEvent(4, sprite, RenderLayer.BEHIND_DIALOGUE)
        );

        scenario.add(0,
                new PositionElementEvent(1, 0, new Vec2(960, 540), Easing.LINEAR, PositionElementEvent.Type.POSITION),
                new PositionElementEvent(2, 0, new Vec2(50, 50), Easing.LINEAR, PositionElementEvent.Type.POSITION),
                new PositionElementEvent(3, 0, new Vec2(50, 500), Easing.LINEAR, PositionElementEvent.Type.POSITION),
                new PositionElementEvent(4, 0, new Vec2(960, 540), Easing.LINEAR, PositionElementEvent.Type.POSITION)
        );

        scenario.add(0,
                new ElementEffectEvent(1, Effect.HOLOGRAM, Axis.Y),
                new ElementEffectEvent(2, Effect.RAINBOW, Axis.X, 1.5f),
                new ElementEffectEvent(3, Effect.BLUR, 5),
                new ElementEffectEvent(4, Effect.OUTLINE, 1, 1 << 1)
        );

        Launcher.launch(new ScenarioScreen(scenario.build()), false);
    }
}

import net.lenni0451.commons.color.Color;
import oxy.bascenario.utils.Launcher;
import oxy.bascenario.api.Scenario;
import oxy.bascenario.api.effects.Easing;
import oxy.bascenario.api.event.element.AddElementEvent;
import oxy.bascenario.api.event.element.values.PositionElementEvent;
import oxy.bascenario.api.event.element.values.RotateElementEvent;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.api.render.elements.LocationInfo;
import oxy.bascenario.api.render.elements.Sprite;
import oxy.bascenario.api.render.elements.shape.Circle;
import oxy.bascenario.api.render.elements.shape.Rectangle;
import oxy.bascenario.api.render.elements.text.FontType;
import oxy.bascenario.api.render.elements.text.Text;
import oxy.bascenario.api.render.elements.text.TextSegment;
import oxy.bascenario.api.utils.FileInfo;
import oxy.bascenario.api.utils.math.Vec2;
import oxy.bascenario.api.utils.math.Vec3;
import oxy.bascenario.screens.ScenarioScreen;

import java.util.ArrayList;
import java.util.List;

public class ElementRenderTest {
    public static void main(String[] args) {
        final Scenario.Builder scenario = new Scenario.Builder();
        scenario.title("Element Test.");

        final Sprite sprite = new Sprite(new FileInfo("CH0326_spr.skel", false, true), new FileInfo("CH0326_spr.atlas", false, true));

        final List<TextSegment> segments = new ArrayList<>();
        segments.add(TextSegment.builder().text("The ").build());
        segments.add(TextSegment.builder().text("quick ").color(Color.CYAN).build());
        segments.add(TextSegment.builder().text("brown ").bold(true).build());
        segments.add(TextSegment.builder().text("fox ").italic(true).build());
        segments.add(TextSegment.builder().text("jump  ").font(new FileInfo("PlaywriteUSTradGuides-Regular.ttf", false, true)).build());
        segments.add(TextSegment.builder().text("over ").type(FontType.SEMI_BOLD).build());
        segments.add(TextSegment.builder().text("the ").type(FontType.BOLD).build());
        segments.add(TextSegment.builder().text("lazy ").shadow(true).build());
        segments.add(TextSegment.builder().text("dog ").underline(true).build());
        final Text text = new Text(segments, 42);

        scenario.add(0,
                new AddElementEvent(-2, new LocationInfo("The gray room", 2000, 500), RenderLayer.ABOVE_DIALOGUE),
                new AddElementEvent(-1, new Rectangle(1920, 1080, Color.GRAY, false), RenderLayer.BEHIND_DIALOGUE),
                new AddElementEvent(0, sprite, RenderLayer.BEHIND_DIALOGUE),
                new AddElementEvent(1, text, RenderLayer.TOP),
                new AddElementEvent(2, new Circle(40, Color.RED, false), RenderLayer.ABOVE_DIALOGUE)
        );

        scenario.add(0,
                new PositionElementEvent(0, 0, new Vec2(960, 540), Easing.LINEAR, PositionElementEvent.Type.POSITION),
                new PositionElementEvent(1, 0, new Vec2(50, 50), Easing.LINEAR, PositionElementEvent.Type.POSITION),
                new PositionElementEvent(2, 0, new Vec2(500, 500), Easing.LINEAR, PositionElementEvent.Type.POSITION)
        );

        scenario.add(0, new PositionElementEvent(0, 1000, new Vec2(100, 540), Easing.LINEAR, PositionElementEvent.Type.POSITION));
        scenario.add(700, new PositionElementEvent(0, 1000, new Vec2(960, 540), Easing.LINEAR, PositionElementEvent.Type.POSITION));

        scenario.add(1000,
                new PositionElementEvent(0, 500, new Vec2(1.5f, 1.5f), Easing.CUBIC, PositionElementEvent.Type.SCALE),
                new PositionElementEvent(1, 0, new Vec2(2, 2), Easing.LINEAR, PositionElementEvent.Type.SCALE),
                new PositionElementEvent(2, 0, new Vec2(2, 2), Easing.LINEAR, PositionElementEvent.Type.SCALE)
        );

        scenario.add(500,
                new PositionElementEvent(0, 500, new Vec2(1, 1), Easing.CUBIC, PositionElementEvent.Type.SCALE),
                new PositionElementEvent(1, 500, new Vec2(1, 1), Easing.CUBIC, PositionElementEvent.Type.SCALE),
                new PositionElementEvent(2, 500, new Vec2(1, 1), Easing.CUBIC, PositionElementEvent.Type.SCALE)
        );

        scenario.add(500,
                new RotateElementEvent(1, 500, new Vec3(0, 0, 50), Easing.SINE)
         );

        Launcher.launch(new ScenarioScreen(scenario.build()), false);
    }
}

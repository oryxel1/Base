import net.lenni0451.commons.color.Color;
import oxy.bascenario.Launcher;
import oxy.bascenario.api.Scenario;
import oxy.bascenario.api.effects.Easing;
import oxy.bascenario.api.event.element.AddElementEvent;
import oxy.bascenario.api.event.element.AttachElementEvent;
import oxy.bascenario.api.event.element.values.PositionElementEvent;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.api.render.elements.Sprite;
import oxy.bascenario.api.render.elements.emoticon.Emoticon;
import oxy.bascenario.api.render.elements.emoticon.EmoticonType;
import oxy.bascenario.api.render.elements.shape.Rectangle;
import oxy.bascenario.api.utils.FileInfo;
import oxy.bascenario.api.utils.math.Vec2;
import oxy.bascenario.screens.ScenarioScreen;

public class EmoticonRenderTest {
    public static void main(String[] args) {
        final Scenario.Builder scenario = new Scenario.Builder();
        scenario.title("Element Attachment Test");

        final Sprite sprite = new Sprite(new FileInfo("CH0326_spr.skel", false, true), new FileInfo("CH0326_spr.atlas", false, true));

        scenario.add(0,
                new AddElementEvent(-1, new Rectangle(1920, 1080, Color.WHITE, false), RenderLayer.BEHIND_DIALOGUE),
                new AddElementEvent(0, sprite, RenderLayer.BEHIND_DIALOGUE),
                new AttachElementEvent(0, 0, new Emoticon(200, EmoticonType.RESPOND, true))
        );

        scenario.add(0, new PositionElementEvent(0, 0, new Vec2(960, 540), Easing.LINEAR, PositionElementEvent.Type.POSITION));

        Launcher.launch(new ScenarioScreen(scenario.build()), false);
    }
}

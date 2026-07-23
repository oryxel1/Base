package scenario;

import net.lenni0451.commons.color.Color;
import oxy.base.utils.Launcher;
import oxy.base.api.Scenario;
import oxy.base.api.effects.Easing;
import oxy.base.api.event.animation.PlayAnimationEvent;
import oxy.base.api.event.element.AddElementEvent;
import oxy.base.api.event.element.AttachElementEvent;
import oxy.base.api.event.element.values.PositionElementEvent;
import oxy.base.api.render.RenderLayer;
import oxy.base.api.render.elements.Sprite;
import oxy.base.api.render.elements.shape.Circle;
import oxy.base.api.utils.FileInfo;
import oxy.base.api.utils.math.Vec2;
import oxy.base.screens.ScenarioScreen;

public class AttachingElementTest {
    public static void main(String[] args) {
        final Scenario.Builder scenario = new Scenario.Builder();

        final Sprite sprite = new Sprite(new FileInfo("CH0326_spr.skel", false, true), new FileInfo("CH0326_spr.atlas", false, true));

        scenario.add(0,
                new AddElementEvent(0, sprite, RenderLayer.BEHIND_DIALOGUE),
                new AttachElementEvent(0, 0, new Circle(40, Color.RED, false))
        );

        scenario.add(0,
                new PositionElementEvent(0, 0, new Vec2(960, 540), Easing.LINEAR, PositionElementEvent.Type.POSITION),
                new PlayAnimationEvent(0, "bascenarioengine:default-shake", false)
        );
        scenario.add(2000, new PositionElementEvent(0, 1000, new Vec2(5000, 540), Easing.LINEAR, PositionElementEvent.Type.POSITION));

        Launcher.launch(new ScenarioScreen(scenario.build()), false);
    }
}

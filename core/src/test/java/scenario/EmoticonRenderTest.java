package scenario;

import net.lenni0451.commons.color.Color;
import oxy.base.utils.Launcher;
import oxy.base.api.Scenario;
import oxy.base.api.effects.Easing;
import oxy.base.api.event.element.AddElementEvent;
import oxy.base.api.event.element.AttachElementEvent;
import oxy.base.api.event.element.values.PositionElementEvent;
import oxy.base.api.render.RenderLayer;
import oxy.base.api.render.elements.Sprite;
import oxy.base.api.render.elements.emoticon.Emoticon;
import oxy.base.api.render.elements.emoticon.EmoticonType;
import oxy.base.api.render.elements.shape.Rectangle;
import oxy.base.api.utils.FileInfo;
import oxy.base.api.utils.math.Vec2;
import oxy.base.screens.ScenarioScreen;

public class EmoticonRenderTest {
    public static void main(String[] args) {
        final Scenario.Builder scenario = new Scenario.Builder();

        final Sprite sprite = new Sprite(new FileInfo("CH0326_spr.skel", false, true), new FileInfo("CH0326_spr.atlas", false, true));

        scenario.add(0,
                new AddElementEvent(0, new Rectangle(1920, 1080, Color.WHITE, false), RenderLayer.BEHIND_DIALOGUE),
                new AddElementEvent(1, sprite, RenderLayer.ABOVE_DIALOGUE)
        );

        scenario.add(0, new PositionElementEvent(1, 0, new Vec2(960, 540), Easing.LINEAR, PositionElementEvent.Type.POSITION));
        for (EmoticonType type : EmoticonType.values()) {
            scenario.add(1000, new AttachElementEvent(1, 0, new Emoticon(1000, type, true)));
        }


        Launcher.launch(new ScenarioScreen(scenario.build()), false);
    }
}

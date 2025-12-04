import net.lenni0451.commons.color.Color;
import oxy.bascenario.Launcher;
import oxy.bascenario.api.Scenario;
import oxy.bascenario.api.effects.Easing;
import oxy.bascenario.api.event.animation.PlayAnimationEvent;
import oxy.bascenario.api.event.animation.SpriteAnimationEvent;
import oxy.bascenario.api.event.element.AddElementEvent;
import oxy.bascenario.api.event.element.values.PositionElementEvent;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.api.render.elements.Sprite;
import oxy.bascenario.api.render.elements.shape.Rectangle;
import oxy.bascenario.api.utils.FileInfo;
import oxy.bascenario.api.utils.math.Vec2;
import oxy.bascenario.screens.ScenarioScreen;

public class AnimationTickTest {
    public static void main(String[] args) {
        final Scenario.Builder scenario = new Scenario.Builder();
        scenario.title("Animation Test.");

        scenario.add(0, new AddElementEvent(-1, new Rectangle(1920, 1080, Color.WHITE, false), RenderLayer.BEHIND_DIALOGUE));

        final Sprite sprite = new Sprite(new FileInfo("CH0326_spr.skel", false, true), new FileInfo("CH0326_spr.atlas", false, true));
        scenario.add(0, new AddElementEvent(0, sprite, RenderLayer.BEHIND_DIALOGUE), new PositionElementEvent(0, 0, new Vec2(960, 540), Easing.LINEAR, PositionElementEvent.Type.POSITION));

        scenario.add(0, new AddElementEvent(1, new Rectangle(200, 200, Color.RED, false), RenderLayer.BEHIND_DIALOGUE), new PositionElementEvent(1, 0, new Vec2(500, 500), Easing.LINEAR, PositionElementEvent.Type.POSITION));
        scenario.add(0, new PlayAnimationEvent(1, "bascenarioengine:loop-test", true));

        scenario.add(0, new SpriteAnimationEvent(0, 0, "Idle_01", 0, true), new SpriteAnimationEvent(0, 0, "06", 0));
        scenario.add(200, new PlayAnimationEvent(0, "bascenarioengine:default-shake", false));
        scenario.add(1000, new PlayAnimationEvent(0, "bascenarioengine:test", false));
        scenario.add(2000, new PlayAnimationEvent(0, "bascenarioengine:down-then-up", false));
        scenario.add(500, new PlayAnimationEvent(0, "bascenarioengine:hangry", false));

        Launcher.launch(new ScenarioScreen(scenario.build()), false);
    }
}

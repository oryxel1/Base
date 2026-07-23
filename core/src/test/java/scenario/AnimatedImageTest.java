package scenario;

import net.lenni0451.commons.color.Color;
import oxy.base.utils.Launcher;
import oxy.base.api.Scenario;
import oxy.base.api.effects.Easing;
import oxy.base.api.event.element.AddElementEvent;
import oxy.base.api.event.element.values.PositionElementEvent;
import oxy.base.api.render.RenderLayer;
import oxy.base.api.render.elements.image.AnimatedImage;
import oxy.base.api.render.elements.shape.Rectangle;
import oxy.base.api.utils.FileInfo;
import oxy.base.api.utils.math.Vec2;
import oxy.base.screens.ScenarioScreen;

public class AnimatedImageTest {
    public static void main(String[] args) {
        final Scenario.Builder scenario = new Scenario.Builder();

        final AnimatedImage image = new AnimatedImage(new FileInfo("hm-hm-hm.gif", false, true), 0, false, Color.WHITE, 618, 640);
        final AnimatedImage loading = new AnimatedImage(new FileInfo("loading.gif", false, true), 0, true, Color.WHITE, 100, 100);

        scenario.add(0,
                new AddElementEvent(0, new Rectangle(1920, 1080, Color.WHITE, false), RenderLayer.BEHIND_DIALOGUE),
                new AddElementEvent(1, image, RenderLayer.BEHIND_DIALOGUE),
                new AddElementEvent(2, loading, RenderLayer.BEHIND_DIALOGUE),
                new PositionElementEvent(2, 0, new Vec2(1920 / 2f - 50, 1080 / 2f - 50), Easing.LINEAR, PositionElementEvent.Type.POSITION)
        );

        Launcher.launch(new ScenarioScreen(scenario.build()), false);
    }
}

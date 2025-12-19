import net.lenni0451.commons.color.Color;
import oxy.bascenario.utils.Launcher;
import oxy.bascenario.api.Scenario;
import oxy.bascenario.api.effects.Easing;
import oxy.bascenario.api.event.element.AddElementEvent;
import oxy.bascenario.api.event.element.values.PositionElementEvent;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.api.render.elements.RendererImage;
import oxy.bascenario.api.render.elements.image.AnimatedImage;
import oxy.bascenario.api.render.elements.shape.Rectangle;
import oxy.bascenario.api.utils.FileInfo;
import oxy.bascenario.api.utils.math.Vec2;
import oxy.bascenario.screens.ScenarioScreen;

public class AnimatedImageTest {
    public static void main(String[] args) {
        final Scenario.Builder scenario = new Scenario.Builder();

        final RendererImage image = new RendererImage(new AnimatedImage(new FileInfo("hm-hm-hm.gif", false, true), false), Color.WHITE, 618, 640);
        final RendererImage loading = new RendererImage(new AnimatedImage(new FileInfo("loading.gif", false, true), true), Color.WHITE, 100, 100);

        scenario.add(0,
                new AddElementEvent(0, new Rectangle(1920, 1080, Color.WHITE, false), RenderLayer.BEHIND_DIALOGUE),
                new AddElementEvent(1, image, RenderLayer.BEHIND_DIALOGUE),
                new AddElementEvent(2, loading, RenderLayer.BEHIND_DIALOGUE),
                new PositionElementEvent(2, 0, new Vec2(1920 / 2f - 50, 1080 / 2f - 50), Easing.LINEAR, PositionElementEvent.Type.POSITION)
        );

        Launcher.launch(new ScenarioScreen(scenario.build()), false);
    }
}

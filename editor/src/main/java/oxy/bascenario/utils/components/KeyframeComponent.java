package oxy.bascenario.utils.components;

import lombok.RequiredArgsConstructor;
import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.component.Component;
import net.lenni0451.rivet.math.Size;

@RequiredArgsConstructor
public class KeyframeComponent extends Component {
    private final KeyframeTest test;

    @Override
    public void render(Renderer renderer, Size size) {
        final float centreX = size.width() / 2f, centreY = size.height() / 2f;
        final float radius = Math.min(centreX, centreY);
        renderer.fillCircle(centreX, centreY, radius, test.isKeyframe() ? Color.fromRGB(131, 117, 42) : Color.WHITE);
    }

    @Override
    public Size computeIdealSize(Size size) {
        return new Size(8, 8);
    }

    public interface KeyframeTest {
        boolean isKeyframe();
    }
}

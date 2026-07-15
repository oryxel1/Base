package oxy.bascenario.utils.components;

import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.component.Component;
import net.lenni0451.rivet.math.Size;

public class TimelineDockExpandButton extends Component {
    @Override
    public void render(Renderer renderer, Size size) {
        renderer.outlineRoundedRect(0, 0, size.width(), size.height(), 5, 1, Color.fromRGB(60, 60, 60));
        renderer.fillRoundedRect(0, 0, size.width(), size.height(), 5, Color.fromRGB(40, 40, 40));
    }

    @Override
    public Size computeIdealSize(Size size) {
        return new Size(32, 18);
    }
}

package oxy.bascenario.editor.timeline;

import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.Renderer;
import net.lenni0451.rivet.component.Component;
import net.lenni0451.rivet.math.Rectangle;
import net.lenni0451.rivet.math.Size;

public class TimelineComponent extends Component {
    @Override
    public void render(Renderer renderer, Rectangle bounds) {
        renderer.fillRect(0, 0, bounds.width(), bounds.height(), Color.fromRGB(35, 35, 35).darker());
        renderer.fillRect(0, 0, bounds.width() / 5, bounds.height(), Color.fromRGB(35, 35, 35));


    }

    @Override
    public Size computeIdealSize(Size constraints) {
        return null; // eh
    }
}

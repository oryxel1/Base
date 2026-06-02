package oxy.bascenario.editor.timeline;

import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.component.Component;
import net.lenni0451.rivet.math.Rectangle;
import net.lenni0451.rivet.math.Size;

public class TrackComponent extends Component {
    public float heightRatio = 1/4f;

    @Override
    public void render(Renderer renderer, Rectangle bounds) {
        renderer.fillRoundedRect(0, bounds.y(), bounds.width(), bounds.height() * heightRatio, 5, Color.fromRGB(35, 35, 35).brighter());
    }

    @Override
    public Size computeIdealSize(Size constraints) {
        return Size.EMPTY;
    }
}

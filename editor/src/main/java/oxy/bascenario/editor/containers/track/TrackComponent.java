package oxy.bascenario.editor.containers.track;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.component.Component;
import net.lenni0451.rivet.math.Rectangle;
import net.lenni0451.rivet.math.Size;
import net.lenni0451.rivet.text.model.TextOrigin;
import oxy.bascenario.editor.containers.TimelineContainer;

@RequiredArgsConstructor
@Accessors(fluent = true)
public class TrackComponent extends Component {
    private final TimelineContainer parent;

    @Setter @Getter
    private int index;
    private float height = 60f;

    @Override
    public void render(Renderer renderer, Rectangle bounds) {
//        renderer.fillRect(0, 0, parent.trackListWidth(), bounds.height(), Color.RED); // here for now...

        renderer.scale(0.5f, () -> renderer.text(this.rivet().backend().shapeText("Track " + index, Color.WHITE), 10, 10, TextOrigin.Horizontal.VISUAL_LEFT, TextOrigin.Vertical.LOGICAL_TOP));

        renderer.fillRoundedRect(parent.trackListWidth(), 0, bounds.width() + 100, bounds.height(), 5, Color.fromRGB(50, 50, 50));
    }

    @Override
    public Size computeIdealSize(Size constraints) {
        return new Size(constraints.width(), height);
    }
}

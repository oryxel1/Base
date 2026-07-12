package oxy.bascenario.editor.containers.timeline;

import lombok.RequiredArgsConstructor;
import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.backend.text.ShapedText;
import net.lenni0451.rivet.component.Component;
import net.lenni0451.rivet.input.mouse.MouseButton;
import net.lenni0451.rivet.input.mouse.MouseButtonEvent;
import net.lenni0451.rivet.input.mouse.MouseMoveEvent;
import net.lenni0451.rivet.math.Size;
import net.lenni0451.rivet.text.model.TextOrigin;
import oxy.bascenario.editor.containers.TimelineContainer;

@RequiredArgsConstructor
public class TimelineTimeSection extends Component {
    private final TimelineContainer parent;

    @Override
    public void render(Renderer renderer, Size size) {
        float x = -parent.trackListContainer().scrollX();
        while (!(x >= size.width())) {
            renderer.fillRect(x, 0, 1, 10, Color.WHITE);

            float time = (x + parent.trackListContainer().scrollX()) / parent.screen().oneSecondWidth();

            time = Math.round(time * 100.0f) / 100.0f;
            ShapedText text = this.rivet().backend().font().shapeText(time + "s", Color.WHITE);
            float currentX = x;

            renderer.scale(0.4f, () -> renderer.text(text, currentX / 0.4f, (25 / 35f) * size.height(), TextOrigin.Horizontal.VISUAL_LEFT, TextOrigin.Vertical.LOGICAL_TOP));

            x += parent.screen().oneSecondWidth();
        }
    }

    @Override
    protected boolean onComponentMouseMove(MouseMoveEvent event, Size bounds) {
        if (event.x() > 0 && event.x() < bounds.width() && event.y() > 0 && event.y() < bounds.height() && event.buttons().contains(MouseButton.LEFT)) {
            float x = parent.trackListContainer().scrollX() + event.x() - 1.25f;
            long newTimestamp = (long) (x / (parent.screen().oneMilSecondWidth()));
            parent.screen().timestamp(Math.max(0, newTimestamp));
        }

        return false;
    }

    @Override
    protected boolean onComponentMouseDown(MouseButtonEvent event, Size bounds) {
        if (event.x() > 0 && event.x() < bounds.width() && event.y() > 0 && event.y() < bounds.height()) {
            float x = parent.trackListContainer().scrollX() + event.x() - 1.25f;
            long newTimestamp = (long) (x / (parent.screen().oneMilSecondWidth()));
            parent.screen().timestamp(Math.max(0, newTimestamp));
        }

        return super.onComponentMouseDown(event, bounds);
    }

    @Override
    public Size computeIdealSize(Size constraints) {
        return new Size(constraints.width(), Math.min(35, constraints.height()));
    }
}

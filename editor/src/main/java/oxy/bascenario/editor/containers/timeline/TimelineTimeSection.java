package oxy.bascenario.editor.containers.timeline;

import lombok.RequiredArgsConstructor;
import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.backend.text.ShapedText;
import net.lenni0451.rivet.component.Component;
import net.lenni0451.rivet.input.mouse.MouseButton;
import net.lenni0451.rivet.input.mouse.MouseButtonEvent;
import net.lenni0451.rivet.input.mouse.MouseMoveEvent;
import net.lenni0451.rivet.math.Rectangle;
import net.lenni0451.rivet.math.Size;
import net.lenni0451.rivet.text.model.TextOrigin;
import oxy.bascenario.editor.ScenarioEditorScreen;
import oxy.bascenario.editor.containers.TimelineContainer;

import static oxy.bascenario.editor.ScenarioEditorScreen.DEFAULT_MAX_TIME;

@RequiredArgsConstructor
public class TimelineTimeSection extends Component {
    private final TimelineContainer parent;

    @Override
    public void render(Renderer renderer, Size bounds) {
        float x = -parent.trackListContainer().scrollX();
        while (!(x >= bounds.width())) {
            renderer.fillRect(x, 0, 1, 10, Color.WHITE);

            final float maxTime = ScenarioEditorScreen.DEFAULT_MAX_TIME * parent.screen().scale();
            float time = (x / bounds.width()) * maxTime;

            float seconds = Math.abs(time) / 1000f;
            seconds = Math.round(seconds * 100.0f) / 100.0f;
            ShapedText text = this.rivet().backend().font().shapeText(seconds + "s", Color.WHITE);
            float currentX = x;

            renderer.scale(0.4f, () -> renderer.text(text, currentX / 0.4f, (25 / 35f) * bounds.height(), TextOrigin.Horizontal.VISUAL_LEFT, TextOrigin.Vertical.LOGICAL_TOP));

            x += bounds.width() / 5f;
        }
    }

    @Override
    protected boolean onComponentMouseMove(MouseMoveEvent event, Size bounds) {
        if (event.x() > 0 && event.x() < bounds.width() && event.y() > 0 && event.y() < bounds.height() && event.buttons().contains(MouseButton.LEFT)) {
            final float ratio = Math.min((event.x() - 1.25f) / (bounds.width()), 1);
            long newTimestamp = (long) (DEFAULT_MAX_TIME * parent.screen().scale() * parent.screen().scroll() + ratio * DEFAULT_MAX_TIME * parent.screen().scale());
            parent.screen().timestamp(Math.max(0, newTimestamp));
        }

        return false;
    }

    @Override
    protected boolean onComponentMouseDown(MouseButtonEvent event, Size bounds) {
        if (event.x() > 0 && event.x() < bounds.width() && event.y() > 0 && event.y() < bounds.height()) {
            final float ratio = Math.min((event.x() - 1.25f) / (bounds.width()) , 1);
            long newTimestamp = (long) (DEFAULT_MAX_TIME * parent.screen().scale() * parent.screen().scroll() + ratio * DEFAULT_MAX_TIME * parent.screen().scale());
            parent.screen().timestamp(Math.max(0, newTimestamp));
        }

        return super.onComponentMouseDown(event, bounds);
    }

    @Override
    public Size computeIdealSize(Size constraints) {
        return new Size(constraints.width(), Math.min(35, constraints.height()));
    }
}

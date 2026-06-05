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
import oxy.bascenario.editor.containers.TimelineContainer;

import static oxy.bascenario.editor.ScenarioEditorScreen.DEFAULT_MAX_TIME;

@RequiredArgsConstructor
public class TimelineTimeSection extends Component {
    private final TimelineContainer parent;

    @Override
    public void render(Renderer renderer, Rectangle bounds) {
        for (int i = 0; i <= 5; i++) {
            long time = (long) ((DEFAULT_MAX_TIME * parent.screen().scale() * parent.screen().scroll()) + (DEFAULT_MAX_TIME * parent.screen().scale() * (i / 5f)));
            float segmentX = TimelineContainer.timestampToPosition(time, 0, bounds.width(), parent.screen().scale(), parent.screen().scroll());

            float seconds = time / 1000f;
            ShapedText text = this.rivet().backend().shapeText(seconds + "s", Color.WHITE);

            renderer.fillRect(segmentX, 0, 1, 10, Color.WHITE);
            renderer.scale(0.4f, () -> renderer.text(text, segmentX / 0.4f, (25 / 35f) * bounds.height(), TextOrigin.Horizontal.VISUAL_LEFT, TextOrigin.Vertical.LOGICAL_TOP));
        }
    }

    @Override
    protected boolean onComponentMouseMove(MouseMoveEvent event, Rectangle bounds) {
        if (event.x() > 0 && event.x() < bounds.width() && event.y() > 0 && event.y() < bounds.height() && event.buttons().contains(MouseButton.LEFT)) {
            final float ratio = Math.min((event.x() - 1.25f) / (bounds.width()), 1);
            long newTimestamp = (long) (DEFAULT_MAX_TIME * parent.screen().scale() * parent.screen().scroll() + ratio * DEFAULT_MAX_TIME * parent.screen().scale());
            parent.screen().timestamp(newTimestamp);
        }

        return false;
    }

    @Override
    protected boolean onComponentMouseDown(MouseButtonEvent event, Rectangle bounds) {
        if (event.x() > 0 && event.x() < bounds.width() && event.y() > 0 && event.y() < bounds.height()) {
            final float ratio = Math.min((event.x() - 1.25f) / (bounds.width()) , 1);
            long newTimestamp = (long) (DEFAULT_MAX_TIME * parent.screen().scale() * parent.screen().scroll() + ratio * DEFAULT_MAX_TIME * parent.screen().scale());
            parent.screen().timestamp(newTimestamp);
        }

        return false;
    }

    @Override
    public Size computeIdealSize(Size constraints) {
        return new Size(constraints.width(), 35);
    }
}

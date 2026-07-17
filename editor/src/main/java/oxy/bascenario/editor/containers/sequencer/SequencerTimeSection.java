package oxy.bascenario.editor.containers.sequencer;

import lombok.RequiredArgsConstructor;
import net.lenni0451.commons.color.Color;
import net.lenni0451.commons.math.MathUtils;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.backend.text.ShapedText;
import net.lenni0451.rivet.component.Component;
import net.lenni0451.rivet.input.mouse.MouseButton;
import net.lenni0451.rivet.input.mouse.MouseButtonEvent;
import net.lenni0451.rivet.input.mouse.MouseMoveEvent;
import net.lenni0451.rivet.math.Size;
import net.lenni0451.rivet.text.model.TextOrigin;
import oxy.bascenario.editor.EditorValues;

@RequiredArgsConstructor
public class SequencerTimeSection extends Component {
    @Override
    public void render(Renderer renderer, Size size) {
        renderer.fillRect(0, size.height() - 4, size.width(), 4, Color.fromRGB(108, 108, 210));

        float x = -EditorValues.instance().scroll();
        while (!(x >= size.width())) {
            float timeX = x + EditorValues.instance().scroll();
            float time = timeX / EditorValues.instance().oneMilSecondWidth();

            ShapedText text = this.rivet().backend().font().shapeText(format(MathUtils.ceilLong(time)), Color.GRAY);
            float currentX = x;

            renderer.scale(0.3f, () -> renderer.text(text, currentX / 0.3f, (5 / 35f) * size.height(), TextOrigin.Horizontal.VISUAL_LEFT, TextOrigin.Vertical.LOGICAL_TOP));

            x += EditorValues.instance().oneSecondWidth();
        }
    }

    @Override
    protected boolean onComponentMouseMove(MouseMoveEvent event, Size bounds) {
        if (event.x() > 0 && event.x() < bounds.width() && event.y() > 0 && event.y() < bounds.height() && event.buttons().contains(MouseButton.LEFT)) {
            float x = EditorValues.instance().scroll() + event.x() - 2.5f;
            long newTimestamp = (long) (x / (EditorValues.instance().oneMilSecondWidth()));

            EditorValues.instance().timestamp(Math.max(0, newTimestamp));
        }

        return false;
    }

    @Override
    protected boolean onComponentMouseDown(MouseButtonEvent event, Size bounds) {
        if (event.x() > 0 && event.x() < bounds.width() && event.y() > 0 && event.y() < bounds.height()) {
            float x = EditorValues.instance().scroll() + event.x() - 2.5f;
            long newTimestamp = (long) (x / (EditorValues.instance().oneMilSecondWidth()));

            EditorValues.instance().timestamp(Math.max(0, newTimestamp));
        }

        return super.onComponentMouseDown(event, bounds);
    }

    @Override
    public Size computeIdealSize(Size constraints) {
        return new Size(constraints.width(), 28);
    }

    private static String format(long ms) {
        long second = (ms / 1000) % 60;
        long minute = (ms / (1000 * 60)) % 60;
        long hour = (ms / (1000 * 60 * 60)) % 24;
        return (hour < 10 ? "0" + hour : hour) + ":" + (minute < 10 ? "0" + minute : minute) + ":" + (second < 10 ? "0" + second : second);
    }
}

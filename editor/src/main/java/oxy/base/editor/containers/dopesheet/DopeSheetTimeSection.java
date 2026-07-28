package oxy.base.editor.containers.dopesheet;

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
import oxy.base.editor.EditorValues;
import oxy.base.editor.object.ObjectOrEvent;

public class DopeSheetTimeSection extends Component {
    @Override
    public void render(Renderer renderer, Size size) {
        final ObjectOrEvent objectOrEvent = EditorValues.instance().selectedObject();
        if (objectOrEvent == null) {
            return;
        }

        renderer.fillRect(0, size.height() - 4, size.width(), 4, Color.fromRGB(108, 108, 210));

        float x = 0;
        while (!(x >= size.width())) {
            float timeX = x;
            float time = objectOrEvent.start + (timeX / EditorValues.instance().oneMilSecondWidth());

            ShapedText text = this.rivet().backend().font().shapeText(String.valueOf(time), Color.GRAY);
            float currentX = x;

            renderer.scale(0.775f, () -> renderer.text(text, currentX / 0.775f, (5 / 35f) * size.height(), TextOrigin.Horizontal.VISUAL_LEFT, TextOrigin.Vertical.LOGICAL_TOP));

            x += EditorValues.instance().oneSecondWidth();
        }
    }

    @Override
    protected boolean onComponentMouseMove(MouseMoveEvent event, Size bounds) {
        if (event.isHeld(MouseButton.LEFT)) {
            time(event.x(), event.y(), bounds);
        }
        return false;
    }

    @Override
    protected boolean onComponentMouseDown(MouseButtonEvent event, Size bounds) {
        time(event.x(), event.y(), bounds);
        return true;
    }

    private void time(float mouseX, float mouseY, Size bounds) {
        final ObjectOrEvent objectOrEvent = EditorValues.instance().selectedObject();
        if (objectOrEvent == null) {
            return;
        }

        if (mouseX > 0 && mouseX < bounds.width() && mouseY > 0 && mouseY < bounds.height()) {
            float x = EditorValues.instance().scroll() + mouseX - 2.5f;
            long newTimestamp = (long) (x / (EditorValues.instance().oneMilSecondWidth()));
            newTimestamp += objectOrEvent.start;
            newTimestamp = Math.round(newTimestamp / 10f) * 10L;

            EditorValues.instance().timestamp(MathUtils.clamp(newTimestamp, 0, objectOrEvent.start + objectOrEvent.duration));
        }
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

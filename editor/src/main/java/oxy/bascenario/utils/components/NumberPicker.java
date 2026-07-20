package oxy.bascenario.utils.components;

import lombok.SneakyThrows;
import net.lenni0451.commons.math.MathUtils;
import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.component.impl.DragNumberInput;
import net.lenni0451.rivet.component.impl.TextField;
import net.lenni0451.rivet.input.keyboard.Key;
import net.lenni0451.rivet.input.keyboard.KeyEvent;
import net.lenni0451.rivet.input.mouse.MouseButtonEvent;
import net.lenni0451.rivet.input.mouse.MouseMoveEvent;
import net.lenni0451.rivet.layer.Layer;
import net.lenni0451.rivet.layer.LayerBucket;
import net.lenni0451.rivet.layout.absolute.AbsoluteLayout;
import net.lenni0451.rivet.layout.absolute.AbsoluteOptions;
import net.lenni0451.rivet.math.Rectangle;
import net.lenni0451.rivet.math.Size;

import java.lang.reflect.Field;

public class NumberPicker extends DragNumberInput {
    private TextField field = new TextField() {
        @Override
        protected boolean onComponentKeyUp(KeyEvent event) {
            if (event.key() == Key.ENTER && layer != null) {
                this.rivet().removeLayer(layer);
                layer = null;

                try {
                    value(Double.parseDouble(field.text()));
                } catch (Exception ignored) {
                }
            }

            return super.onComponentKeyUp(event);
        }
    };
    private Layer layer;

    public NumberPicker(double min, double max, double value) {
        super(min, max, value);
    }

    public NumberPicker(double min, double max, double step, double value) {
        super(min, max, step, value);
    }

    private boolean skipNextUp;

    @SneakyThrows
    @Override
    protected boolean onComponentMouseMove(MouseMoveEvent event, Size size) {
        final Field field = DragNumberInput.class.getDeclaredField("dragging");
        field.setAccessible(true);
        if (field.getBoolean(this)) {
            this.skipNextUp = true;
        }

        return super.onComponentMouseMove(event, size);
    }

    protected void updateComponentPosition(Rectangle bounds) {
        if (layer == null) {
            return;
        }

        field.cornerRadius().set(this.cornerRadius().value());
        field.layoutOptions(new AbsoluteOptions(bounds.x(), bounds.y(), bounds.width(), bounds.height()));
    }

    @Override
    protected boolean onComponentMouseUp(MouseButtonEvent event, Size size) {
        if (!this.skipNextUp) {
            field.text(String.valueOf(value()));
            final Container container = new Container(AbsoluteLayout.INSTANCE);
            if (field.parent() != null) {
                field.setRivet(null, null);
            }

            Rectangle bounds = this.absoluteBounds();
            field.cornerRadius().set(this.cornerRadius().value());
            field.layoutOptions(new AbsoluteOptions(bounds.x(), bounds.y(), bounds.width(), bounds.height()));
            container.addChild(field);

            this.layer = new Layer(container, LayerBucket.OVERLAY);
            this.rivet().addLayer(this.layer);
        }

        this.skipNextUp = false;
        return super.onComponentMouseUp(event, size);
    }
}

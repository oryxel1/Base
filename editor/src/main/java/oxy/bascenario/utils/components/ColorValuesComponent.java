package oxy.bascenario.utils.components;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.component.Component;
import net.lenni0451.rivet.component.ListenerList;
import net.lenni0451.rivet.input.mouse.MouseButton;
import net.lenni0451.rivet.input.mouse.MouseMoveEvent;
import net.lenni0451.rivet.math.Size;
import net.lenni0451.rivet.text.model.TextOrigin;

import java.text.DecimalFormat;
import java.util.function.Consumer;

@Accessors(fluent = true)
public class ColorValuesComponent extends Component {
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.000");
    private static final Color MAIN = Color.fromRGB(71, 114, 179);

    @Setter
    private Color color;

    @Getter
    private final ListenerList<Consumer<Color>> colorChangeListener = new ListenerList<>();

    public ColorValuesComponent(Color color) {
        this.color = color;
    }

    @Override
    public void render(Renderer renderer, Size size) {
        renderer.fillRoundedRect(0, 0, size.width(), size.height(), 5, Color.fromRGB(84, 84, 84));

        renderer.fillRoundedRect(0, 0, size.width() * color.getRedF(), 20, 5, 0, 0, color.getRedF() > 0.98f ? 5 : 0, MAIN);
        renderer.fillRect(0, 20, size.width() * color.getGreenF(), 20, MAIN);
        renderer.fillRect(0, 40, size.width() * color.getBlueF(), 20, MAIN);
        renderer.fillRoundedRect(0, 60, size.width() * color.getAlphaF(), 20, 0, 5, color.getAlphaF() > 0.98f ? 5 : 0, 0, MAIN);

        renderer.fillRect(0, 20, size.width(), 1, Color.fromRGB(60, 60, 60));
        renderer.fillRect(0, 40, size.width(), 1, Color.fromRGB(60, 60, 60));
        renderer.fillRect(0, 60, size.width(), 1, Color.fromRGB(60, 60, 60));

        renderer.outlineRoundedRect(0, 0, size.width(), size.height(), 5, 1, Color.fromRGB(60, 60, 60));

        renderer.text(rivet().backend().font().derive(12).shapeText("Red", Color.WHITE), 10, 20 / 2f, TextOrigin.Horizontal.VISUAL_LEFT, TextOrigin.Vertical.VISUAL_CENTER);
        renderer.text(rivet().backend().font().derive(12).shapeText("Green", Color.WHITE), 10, 20 + 20 / 2f, TextOrigin.Horizontal.VISUAL_LEFT, TextOrigin.Vertical.VISUAL_CENTER);
        renderer.text(rivet().backend().font().derive(12).shapeText("Blue", Color.WHITE), 10, 40 + 20 / 2f, TextOrigin.Horizontal.VISUAL_LEFT, TextOrigin.Vertical.VISUAL_CENTER);
        renderer.text(rivet().backend().font().derive(12).shapeText("Alpha", Color.WHITE), 10, 60 + 20 / 2f, TextOrigin.Horizontal.VISUAL_LEFT, TextOrigin.Vertical.VISUAL_CENTER);

        renderer.text(rivet().backend().font().derive(12).shapeText(String.valueOf(DECIMAL_FORMAT.format(color.getRedF())), Color.WHITE), size.width() - 10, 20 / 2f, TextOrigin.Horizontal.VISUAL_RIGHT, TextOrigin.Vertical.VISUAL_CENTER);
        renderer.text(rivet().backend().font().derive(12).shapeText(String.valueOf(DECIMAL_FORMAT.format(color.getGreenF())), Color.WHITE), size.width() - 10, 20 + 20 / 2f, TextOrigin.Horizontal.VISUAL_RIGHT, TextOrigin.Vertical.VISUAL_CENTER);
        renderer.text(rivet().backend().font().derive(12).shapeText(String.valueOf(DECIMAL_FORMAT.format(color.getBlueF())), Color.WHITE), size.width() - 10, 40 + 20 / 2f, TextOrigin.Horizontal.VISUAL_RIGHT, TextOrigin.Vertical.VISUAL_CENTER);
        renderer.text(rivet().backend().font().derive(12).shapeText(String.valueOf(DECIMAL_FORMAT.format(color.getAlphaF())), Color.WHITE), size.width() - 10, 60 + 20 / 2f, TextOrigin.Horizontal.VISUAL_RIGHT, TextOrigin.Vertical.VISUAL_CENTER);
    }

    @Override
    protected boolean onComponentMouseMove(MouseMoveEvent event, Size size) {
        if (event.isHeld(MouseButton.LEFT)) {
            update(size, event.x(), event.y());
        }

        return true;
    }

    private void update(Size size, float x, float y) {
        float factor = x / size.width();
        if (factor < 0 || factor > 1) {
            return;
        }

        if (y <= 20) {
            color = color.withRedF(factor);
        } else if (y <= 40) {
            color = color.withGreenF(factor);
        } else if (y <= 60) {
            color = color.withBlueF(factor);
        } else if (y <= 80) {
            color = color.withAlphaF(factor);
        }

        this.colorChangeListener.callVoid((c) -> c.accept(color));
    }

    @Override
    public Size computeIdealSize(Size size) {
        return new Size(205, 80);
    }
}

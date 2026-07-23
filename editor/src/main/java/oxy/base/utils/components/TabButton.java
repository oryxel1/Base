package oxy.base.utils.components;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.backend.text.ShapedText;
import net.lenni0451.rivet.component.Component;
import net.lenni0451.rivet.input.mouse.MouseButton;
import net.lenni0451.rivet.input.mouse.MouseButtonEvent;
import net.lenni0451.rivet.math.Size;
import net.lenni0451.rivet.text.model.TextOrigin;

@RequiredArgsConstructor
@Accessors(fluent = true, chain = true, makeFinal = true)
public class TabButton extends Component {
    @Setter
    private boolean line;
    private ShapedText shapedText;

    private final String text;
    private final ClickListener clickListener;

    @Override
    protected boolean onComponentMouseUp(MouseButtonEvent event, Size size) {
        if (event.button() == MouseButton.LEFT) {
            clickListener.onClick(this);
            return true;
        }

        return false;
    }

    @Override
    public void render(final Renderer renderer, final Size size) {
        if (shapedText == null) {
            shapedText = this.rivet().backend().font().derive(20).shapeText(this.text, Color.WHITE);
        }

        float x = TextOrigin.Horizontal.VISUAL_CENTER.position(size.width());
        float y = TextOrigin.Vertical.LOGICAL_TOP.position(size.height());
        renderer.text(shapedText, x, y, TextOrigin.Horizontal.VISUAL_CENTER, TextOrigin.Vertical.LOGICAL_TOP);

        if (line) {
            renderer.fillRect(0, shapedText.logicalBounds().height(), size.width(), 1, Color.WHITE);
        }
    }

    @Override
    public Size computeIdealSize(final Size constraints) {
        if (shapedText == null) {
            shapedText = this.rivet().backend().font().derive(20).shapeText(this.text, Color.WHITE);
        }

        return new Size(shapedText.visualBounds().width() + 10f, shapedText.logicalBounds().height() + 10);
    }

    @FunctionalInterface
    public interface ClickListener {
        void onClick(final TabButton button);
    }
}

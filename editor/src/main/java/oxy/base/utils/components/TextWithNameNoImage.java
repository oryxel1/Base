package oxy.base.utils.components;

import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.Texture;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.backend.text.ShapedText;
import net.lenni0451.rivet.component.Component;
import net.lenni0451.rivet.component.Parent;
import net.lenni0451.rivet.math.Rectangle;
import net.lenni0451.rivet.math.Size;
import net.lenni0451.rivet.text.model.TextOrigin;
import oxy.base.Base;
import oxy.base.utils.animation.math.ColorAnimations;

import java.util.List;

public class TextWithNameNoImage extends Component implements Parent {
    private final String name;
    private final Component component;
    private final ColorAnimations backgroundColor = new ColorAnimations(Color.fromRGBA(63, 63, 63, 0));

    public TextWithNameNoImage(String name, Component component) {
        this.name = name;
        this.component = component;
    }

    @Override
    protected void onAddedInternal() {
        component.setRivet(rivet(), this);
    }

    @Override
    public void renderInternal(Renderer renderer, Size bounds) {
        renderer.fillRoundedRect(0, 0, bounds.width(), bounds.height(), 5, backgroundColor.color());
        component.render(renderer, bounds);

        ShapedText text = this.rivet().backend().font().shapeText(name, Color.WHITE);
        renderer.text(text,
                (102 / 2f - text.visualBounds().width() / 2f), 108, TextOrigin.Horizontal.VISUAL_LEFT, TextOrigin.Vertical.VISUAL_TOP
        );
    }

    @Override
    protected void onMouseEnterInternal() {
        backgroundColor.set(Color.fromRGB(63, 63, 63), 200);
    }

    @Override
    protected void onMouseLeaveInternal() {
        backgroundColor.set(Color.fromRGBA(63, 63, 63, 0), 200);
    }

    @Override
    public Size computeIdealSize(Size constraints) {
        return new Size(102,128);
    }

    @Override
    public void requestLayoutRecalculation() {
    }

    @Override
    public Size contentSize() {
        return Size.EMPTY;
    }

    @Override
    public List<Component> children() {
        return List.of(component);
    }

    @Override
    public Rectangle childBounds(Component component) {
        return Rectangle.EMPTY;
    }
}

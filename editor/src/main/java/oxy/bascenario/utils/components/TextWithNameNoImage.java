package oxy.bascenario.utils.components;

import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.Texture;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.backend.text.ShapedText;
import net.lenni0451.rivet.backend.thingl.ThinGLTexture;
import net.lenni0451.rivet.component.Component;
import net.lenni0451.rivet.component.Parent;
import net.lenni0451.rivet.math.Rectangle;
import net.lenni0451.rivet.math.Size;
import net.lenni0451.rivet.text.model.TextOrigin;
import oxy.bascenario.Base;
import oxy.bascenario.utils.animation.math.ColorAnimations;

import java.util.List;

public class TextWithNameNoImage extends Component {
    private final String name;
    private final Component component;
    private final ColorAnimations backgroundColor = new ColorAnimations(Color.fromRGBA(63, 63, 63, 0));

    public TextWithNameNoImage(String name, Component component) {
        this.name = name;
        this.component = component;
    }

    @Override
    public void render(Renderer renderer, Size bounds) {
        if (component.rivet() == null) {
            component.setRivet(rivet(), new Parent() {
                @Override
                public void requestLayoutRecalculation() {
                }

                @Override
                public Size contentSize() {
                    return Size.EMPTY;
                }

                @Override
                public List<Component> children() {
                    return List.of();
                }

                @Override
                public Rectangle absoluteBounds() {
                    return new Rectangle(Size.EMPTY);
                }

                @Override
                public Rectangle childBounds(Component component) {
                    return new Rectangle(Size.EMPTY);
                }
            });
        }

        renderer.fillRoundedRect(0, 0, bounds.width(), bounds.height(), 5, backgroundColor.color());
        component.render(renderer, bounds);

        ShapedText text = this.rivet().backend().font().shapeText(name, Color.WHITE);
        renderer.scale(0.4f, () ->
                renderer.text(text,
                        (102 / 2f - text.visualBounds().width() * .4f / 2f) / .4f, 108 / .4f, TextOrigin.Horizontal.VISUAL_LEFT, TextOrigin.Vertical.VISUAL_TOP
                ));
    }

    @Override
    protected void onComponentMouseEnter() {
        backgroundColor.set(Color.fromRGB(63, 63, 63), 200);
    }

    @Override
    protected void onComponentMouseLeave() {
        backgroundColor.set(Color.fromRGBA(63, 63, 63, 0), 200);
    }

    @Override
    public Size computeIdealSize(Size constraints) {
        return new Size(102,128);
    }
}

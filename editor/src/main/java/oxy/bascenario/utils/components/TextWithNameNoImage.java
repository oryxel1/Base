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

import java.util.List;

public class TextWithNameNoImage extends Component {
    private final String name;
    private final Component component;

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

        renderer.fillRect(0, 0, bounds.width(), bounds.height(), Color.fromRGB(25, 25, 25));
        component.render(renderer, bounds);

        ShapedText text = this.rivet().backend().font().shapeText(name, Color.WHITE);
        renderer.scale(0.4f, () ->
                renderer.text(text,
                        (277 / 2f - text.visualBounds().width() * .4f / 2f) / .4f, 160 / .4f, TextOrigin.Horizontal.VISUAL_LEFT, TextOrigin.Vertical.VISUAL_TOP
                        ));
    }

    @Override
    public Size computeIdealSize(Size constraints) {
        return new Size(255 + 20, 141 + 40);
    }
}

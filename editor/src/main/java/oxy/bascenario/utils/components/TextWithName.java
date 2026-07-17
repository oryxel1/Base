package oxy.bascenario.utils.components;

import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.Texture;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.backend.text.ShapedText;
import net.lenni0451.rivet.backend.thingl.ThinGLTexture;
import net.lenni0451.rivet.component.Component;
import net.lenni0451.rivet.math.Size;
import net.lenni0451.rivet.text.model.TextOrigin;
import oxy.bascenario.Base;
import oxy.bascenario.utils.animation.math.ColorAnimations;

public class TextWithName extends Component {
    private final String name;
    private final Texture texture;
    private final ColorAnimations backgroundColor = new ColorAnimations(Color.fromRGBA(63, 63, 63, 0));

    public TextWithName(String name, String path) {
        this.name = name;
        this.texture = new ThinGLTexture(Base.instance().assetsManager().texture(path));
    }

    @Override
    public void render(Renderer renderer, Size bounds) {
        renderer.fillRoundedRect(0, 0, bounds.width(), bounds.height(), 5, backgroundColor.color());
        renderer.image(this.texture, 5, 5, 96, 96, Color.WHITE);

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

package oxy.bascenario.util.components;

import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.Texture;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.backend.text.ShapedText;
import net.lenni0451.rivet.backend.thingl.ThinGLTexture;
import net.lenni0451.rivet.component.Component;
import net.lenni0451.rivet.math.Rectangle;
import net.lenni0451.rivet.math.Size;
import net.lenni0451.rivet.text.model.TextOrigin;
import oxy.bascenario.Base;

public class TextWithName extends Component {
    private final String name;
    private final Texture texture;

    public TextWithName(String name, String path) {
        this.name = name;
        this.texture = new ThinGLTexture(Base.instance().assetsManager().texture(path));
    }

    @Override
    public void render(Renderer renderer, Rectangle bounds) {
        renderer.fillRoundedRect(0, 0, bounds.width(), bounds.height(), 5, Color.fromRGB(25, 25, 25));
        renderer.image(this.texture, 10, 10, 255, 141, Color.WHITE);

        ShapedText text = this.rivet().backend().shapeText(name, Color.WHITE);
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

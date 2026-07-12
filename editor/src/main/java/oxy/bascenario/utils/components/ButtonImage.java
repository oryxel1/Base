package oxy.bascenario.utils.components;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.backend.thingl.ThinGLTexture;
import net.lenni0451.rivet.component.Component;
import net.lenni0451.rivet.input.mouse.MouseButton;
import net.lenni0451.rivet.input.mouse.MouseButtonEvent;
import net.lenni0451.rivet.math.Size;
import net.lenni0451.rivet.theme.ThemeOption;
import net.raphimc.thingl.gl.resource.image.texture.impl.Texture2D;
import oxy.bascenario.utils.animation.math.ColorAnimations;

@RequiredArgsConstructor
@Accessors(fluent = true, chain = true, makeFinal = true)
public class ButtonImage extends Component {
    private boolean hovered = false;

    @Getter
    private final ThinGLTexture texture;

    private final ClickListener clickListener;

    @Getter
    private final ThemeOption<Color> hoverColor;

    @Getter
    private final ThemeOption<Integer> blendDuration;

    private final ColorAnimations color = new ColorAnimations(Color.WHITE);

    public ButtonImage(final Texture2D texture, final ClickListener clickListener) {
        this.texture = new ThinGLTexture(texture);
        this.clickListener = clickListener;

        this.hoverColor = new ThemeOption<>(this, null);
        this.hoverColor.set(Color.WHITE);

        this.blendDuration = new ThemeOption<>(this, null);
        this.blendDuration.set(800);
    }

    @Override
    protected boolean onComponentMouseUp(MouseButtonEvent event, Size size) {
        if (event.button() == MouseButton.LEFT) {
            clickListener.onClick(event);
            return true;
        }

        return false;
    }

    @Override
    public void render(final Renderer renderer, final Size size) {
        renderer.image(this.texture, 0, 0, size.width(), size.height(), color.color());
    }

    @Override
    public Size computeIdealSize(final Size constraints) {
        return new Size(this.texture.width(), this.texture.height());
    }

    @Override
    protected void onComponentRemoved() {
        this.hovered = false;
    }

    @Override
    protected void onComponentDisabled() {
        this.hovered = false;
    }

    @Override
    protected void onComponentMouseEnter() {
        this.hovered = true;
        color.set(this.hoverColor.value(), this.blendDuration.value());
    }

    @Override
    protected void onComponentMouseLeave() {
        this.hovered = false;
        color.set(Color.WHITE, this.blendDuration.value());
    }

    @FunctionalInterface
    public interface ClickListener {
        void onClick(final MouseButtonEvent event);
    }
}

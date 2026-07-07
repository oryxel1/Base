package oxy.bascenario.util.components;

import it.unimi.dsi.fastutil.booleans.BooleanPredicate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.Texture;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.backend.thingl.ThinGLTexture;
import net.lenni0451.rivet.component.Component;
import net.lenni0451.rivet.input.mouse.MouseButton;
import net.lenni0451.rivet.input.mouse.MouseButtonEvent;
import net.lenni0451.rivet.math.Size;
import net.lenni0451.rivet.theme.ThemeOption;
import oxy.bascenario.Base;
import oxy.bascenario.utils.animation.math.ColorAnimations;

import java.util.function.Consumer;

@RequiredArgsConstructor
@Accessors(fluent = true, chain = true, makeFinal = true)
public class PlayButton extends Component {
    @Setter
    private boolean state;
    private Consumer<Boolean> onStateChanged;

    @Getter
    private final ThemeOption<Integer> blendDuration;

    private final ColorAnimations color = new ColorAnimations(Color.WHITE);

    private Texture texture1, texture2;
    public PlayButton(boolean state, Consumer<Boolean> onStateChanged) {
        this.state = state;
        this.onStateChanged = onStateChanged;

        this.blendDuration = new ThemeOption<>(this, null);
        this.blendDuration.set(800);

        this.texture1 = new ThinGLTexture(Base.instance().assetsManager().texture("assets/base/uis/editor/pause_18.png"));
        this.texture2 = new ThinGLTexture(Base.instance().assetsManager().texture("assets/base/uis/editor/play_18.png"));
    }

    @Override
    protected boolean onComponentMouseUp(MouseButtonEvent event, Size size) {
        if (event.button() == MouseButton.LEFT) {
            this.state = !this.state;
            onStateChanged.accept(this.state);
            return true;
        }

        return false;
    }

    @Override
    public void render(final Renderer renderer, final Size size) {
        renderer.image(state ? this.texture1 : this.texture2, 0, 0, size.width(), size.height(), color.color());
    }

    @Override
    public Size computeIdealSize(final Size constraints) {
        return new Size(18, 18);
    }

    @Override
    protected void onComponentMouseEnter() {
        color.set(Color.fromRGB(202, 74, 92), this.blendDuration.value());
    }

    @Override
    protected void onComponentMouseLeave() {
        color.set(Color.WHITE, this.blendDuration.value());
    }

    @FunctionalInterface
    public interface ClickListener {
        void onClick(final MouseButtonEvent event);
    }
}

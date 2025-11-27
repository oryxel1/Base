package oxy.bascenario.screens.renderer.base;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.lenni0451.commons.animation.DynamicAnimation;
import net.lenni0451.commons.animation.easing.EasingFunction;
import net.lenni0451.commons.color.Color;
import net.lenni0451.commons.color.ColorUtils;
import net.raphimc.thingl.ThinGL;
import oxy.bascenario.api.elements.effect.OverlayEffect;
import oxy.bascenario.api.elements.effect.OverlayEffectType;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.api.utils.Axis;
import oxy.bascenario.utils.AnimationUtils;
import oxy.bascenario.utils.ColorAnimations;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
public class ElementRenderer<T> {
    protected final T element;
    @Getter
    private final RenderLayer layer;

    protected DynamicAnimation x = AnimationUtils.dummy(0), y = AnimationUtils.dummy(0);
    public void moveX(float x, long duration) {
        this.x = AnimationUtils.build(duration, this.x.getValue(), x, EasingFunction.LINEAR);
    }
    public void moveY(float y, long duration) {
        this.y = AnimationUtils.build(duration, this.y.getValue(), y, EasingFunction.LINEAR);
    }
    public void move(float x, float y, long duration) {
        moveX(x, duration);
        moveY(y, duration);
    }
    protected ColorAnimations color = new ColorAnimations(Color.WHITE);
    public void overlayColor(Color color, long duration) {
        this.color.set(color, duration);
    }
    protected DynamicAnimation scale = AnimationUtils.dummy(1);
    // I think scale should allow choosing easing function to make it looks better, however for
    // x and y and color linear is fine else it will looks weird lol.
    public void scale(float scale, long duration, EasingFunction function) {
        this.scale = AnimationUtils.build(duration, this.scale.getValue(), scale, function);
    }
    @Getter
    protected final Set<OverlayEffect> effects = new HashSet<>();

    public final void renderAll() {
        render();
        renderEffects();
    }

    protected void renderEffects() {
        if (this.effects.isEmpty()) {
            return;
        }

        ThinGL.programs().getColorTweak().bindInput();
        this.render();
        ThinGL.programs().getColorTweak().unbindInput();
        this.effects.forEach(effect -> renderEffect(effect.getType(), effect.getAxis()));
        ThinGL.programs().getColorTweak().clearInput();
    }

    protected final void renderEffect(OverlayEffectType effect, Axis axis) {
        switch (effect) {
            case HOLOGRAM -> {
                ThinGL.programs().getColorTweak().configureParameters(Color.fromRGBA(30, 97, 205, 60));
                ThinGL.programs().getColorTweak().render(0, 0, 1920, 1080);
                ThinGL.programs().getColorTweak().configureParameters(Color.GRAY.withAlphaF(0.2f));
                if (axis == Axis.X) {
                    for (int x = (int) -(1920 * 20f); x < 0; x += 200) {
                        long index = System.currentTimeMillis() + x;
                        index %= (long) (1920 * 20f);

                        ThinGL.programs().getColorTweak().render((index / 20f), 0, (index / 20f) + 4, 1080);
                    }
                } else {
                    for (int y = (int) -(1080 * 20f); y < 0; y += 200) {
                        long index = System.currentTimeMillis() + y;
                        index %= (long) (1080 * 20f);

                        ThinGL.programs().getColorTweak().render(0, (index / 20f), 1920, (index / 20f) + 4);
                    }
                }
            }

            case RAINBOW -> {
                if (axis == Axis.X) {
                    for (int x = 0; x < 1920; x += 5) {
                        ThinGL.programs().getColorTweak().configureParameters(Color.fromRGB(ColorUtils.getRainbowColor(x, 3.5f).getRGB()).withAlphaF(0.3f));
                        ThinGL.programs().getColorTweak().render(x, 0, x + 5, 1080);
                    }
                } else {
                    for (int y = 0; y < 1080; y += 5) {
                        ThinGL.programs().getColorTweak().configureParameters(Color.fromRGB(ColorUtils.getRainbowColor(y, 3.5f).getRGB()).withAlphaF(0.3f));
                        ThinGL.programs().getColorTweak().render(0, y, 1920, y + 5);
                    }
                }
            }
        }
    }

    protected void render() {
    }

    // A bit misleading, will also get called on start...
    public void resize(int width, int height) {}
    public void dispose() {}
}

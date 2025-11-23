package oxy.bascenario.screens.renderer.base;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.lenni0451.commons.animation.DynamicAnimation;
import net.lenni0451.commons.animation.easing.EasingFunction;
import net.lenni0451.commons.color.Color;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.utils.AnimationUtils;
import oxy.bascenario.utils.ColorAnimations;

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

    // A bit misleading, will also get called on start...
    public void resize(int width, int height) {}
    public void render() {}
    public void dispose() {}
}

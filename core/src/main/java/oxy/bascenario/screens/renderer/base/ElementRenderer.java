package oxy.bascenario.screens.renderer.base;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.lenni0451.commons.animation.DynamicAnimation;
import net.lenni0451.commons.animation.easing.EasingFunction;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.utils.AnimationUtils;

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

    // A bit misleading, will also get called on start...
    public void resize(int width, int height) {}
    public void render() {}
    public void dispose() {}
}

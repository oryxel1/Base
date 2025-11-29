package oxy.bascenario.screens.renderer.element.base;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.lenni0451.commons.animation.easing.EasingFunction;
import net.lenni0451.commons.color.Color;
import oxy.bascenario.api.effects.Effect;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.screens.renderer.AnimationTicker;
import oxy.bascenario.utils.animation.math.ColorAnimations;
import oxy.bascenario.utils.animation.math.Vec2Animations;
import oxy.bascenario.utils.animation.math.Vec3Animations;
import oxy.bascenario.api.utils.math.Vec2;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@RequiredArgsConstructor
@Getter
public abstract class ElementRenderer<T> {
    protected final T element;
    private final RenderLayer layer;
    private final long start = System.currentTimeMillis();

    protected final Map<String, AnimationTicker> animations = new HashMap<>();

    protected final Vec2Animations position = new Vec2Animations(), offset = new Vec2Animations(), scale = new Vec2Animations(EasingFunction.LINEAR, new Vec2(1, 1));
    protected final ColorAnimations color = new ColorAnimations(Color.WHITE);
    protected final Vec3Animations rotation = new Vec3Animations();

    protected final Map<Effect, Object[]> effects = new HashMap<>();

    public final void renderAll() {
        render();

        this.animations.values().forEach(AnimationTicker::tick);
        this.animations.values().removeIf(AnimationTicker::safeToRemove);
    }

    protected abstract void render();
    public boolean selfDestruct() {
        return false; // Allow the element to automatically delete itself :D
    }

    // A bit misleading, will also get called on start...
    public void resize(int width, int height) {}
    public void dispose() {}
}

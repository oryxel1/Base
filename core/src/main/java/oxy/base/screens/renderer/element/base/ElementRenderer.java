package oxy.base.screens.renderer.element.base;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.lenni0451.commons.animation.easing.EasingFunction;
import net.lenni0451.commons.color.Color;
import oxy.base.api.effects.Effect;
import oxy.base.api.render.RenderLayer;
import oxy.base.screens.ScenarioScreen;
import oxy.base.screens.renderer.AnimationTicker;
import oxy.base.utils.TimeUtils;
import oxy.base.utils.animation.math.ColorAnimations;
import oxy.base.utils.animation.math.Vec2Animations;
import oxy.base.utils.animation.math.Vec3Animations;
import oxy.base.api.utils.math.Vec2;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Getter
public abstract class ElementRenderer<T> {
    protected final T element;
    private final RenderLayer layer;
    private final long start = TimeUtils.currentTimeMillis();

    protected final Map<String, AnimationTicker> animations = new HashMap<>();

    protected final Vec2Animations position = new Vec2Animations(), offset = new Vec2Animations(), animationOffset = new Vec2Animations(), pivot = new Vec2Animations();
    protected final Vec2Animations scale = new Vec2Animations(EasingFunction.LINEAR, new Vec2(1, 1));
    protected final ColorAnimations overlayColor = new ColorAnimations(Color.TRANSPARENT), color = new ColorAnimations(Color.WHITE);
    protected final Vec3Animations rotation = new Vec3Animations();

    protected final Map<Effect, Object[]> effects = new HashMap<>();

    // This will allow attaching elements... Of course, I won't allow for element inside element and inside another element.
    // However, I will allow users to bypass that using the api, since well if they go that far, why not?
    protected final Map<Integer, ElementRenderer<?>> subElements = new HashMap<>();

    public final void renderAll(ScenarioScreen screen) {
        render(screen);

        this.animations.values().forEach(AnimationTicker::tick);
        this.animations.values().removeIf(AnimationTicker::safeToRemove);
    }

    protected abstract void render(ScenarioScreen screen);
    public boolean selfDestruct() {
        return false; // Allow the element to automatically delete itself :D
    }

    // A bit misleading, will also get called on start...
    public void resize(int width, int height) {}
    public void dispose() {}
}

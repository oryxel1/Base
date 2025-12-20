package oxy.bascenario.utils.animation.math;

import oxy.bascenario.utils.DynamicAnimation;
import net.lenni0451.commons.animation.easing.EasingFunction;
import oxy.bascenario.utils.animation.AnimationUtils;
import oxy.bascenario.api.utils.math.Vec2;

public class Vec2Animations {
    private EasingFunction function;
    private DynamicAnimation x, y;

    public Vec2Animations() {
        this.function = EasingFunction.LINEAR;
        this.x = AnimationUtils.dummy(0);
        this.y = AnimationUtils.dummy(0);
    }

    public Vec2Animations(EasingFunction function, Vec2 vec2) {
        this.function = function;
        this.x = AnimationUtils.dummy(vec2.x());
        this.y = AnimationUtils.dummy(vec2.y());
    }

    public void set(EasingFunction function, Vec2 vec2, long duration, boolean ignoreIfPlaying) {
        this.function = function;
        if (duration <= 0) {
            this.x = AnimationUtils.dummy(vec2.x());
            this.y = AnimationUtils.dummy(vec2.y());
            return;
        }

        if (!this.x.isRunning() || ignoreIfPlaying) {
            this.x = AnimationUtils.build(duration, this.x.getValue(), vec2.x(), this.function);
        }
        if (!this.y.isRunning() || ignoreIfPlaying) {
            this.y = AnimationUtils.build(duration, this.y.getValue(), vec2.y(), this.function);
        }
    }

    public void add(EasingFunction function, Vec2 vec2, long duration, boolean ignoreIfPlaying) {
        set(function, new Vec2(vec2.x() + x(), vec2.y() + y()), duration, ignoreIfPlaying);
    }

    public void set(EasingFunction function, Vec2 vec2, long duration) {
        this.set(function, vec2, duration, true);
    }

    public boolean playing() {
        return this.x.isRunning() || this.y.isRunning();
    }

    public float x() {
        return this.x.getValue();
    }

    public float y() {
        return this.y.getValue();
    }
}

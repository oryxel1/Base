package oxy.bascenario.utils;

import lombok.experimental.UtilityClass;
import net.lenni0451.commons.animation.DynamicAnimation;
import net.lenni0451.commons.animation.easing.EasingFunction;
import net.lenni0451.commons.animation.easing.EasingMode;

@UtilityClass
public class AnimationUtils {
    public DynamicAnimation dummy(float value) {
        return new DummyAnimation(value);
    }

    public DynamicAnimation build(long duration, float start, float end, EasingFunction function) {
        final DynamicAnimation animation = new DynamicAnimation(function, EasingMode.EASE_IN_OUT, duration, start);
        return animation.setTarget(end);
    }

    public static class DummyAnimation extends DynamicAnimation{
        public DummyAnimation(float target) {
            super(EasingFunction.LINEAR, EasingMode.EASE_IN_OUT, 1, target);
        }
    }
}

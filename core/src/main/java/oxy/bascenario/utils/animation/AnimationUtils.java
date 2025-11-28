package oxy.bascenario.utils.animation;

import lombok.experimental.UtilityClass;
import net.lenni0451.commons.animation.DynamicAnimation;
import net.lenni0451.commons.animation.easing.EasingFunction;
import net.lenni0451.commons.animation.easing.EasingMode;
import oxy.bascenario.api.effects.Easing;

@UtilityClass
public class AnimationUtils {
    public EasingFunction toFunction(Easing easing) {
        return switch (easing) {
            case LINEAR -> EasingFunction.LINEAR;
            case SINE -> EasingFunction.SINE;
            case QUAD -> EasingFunction.QUAD;
            case CUBIC -> EasingFunction.CUBIC;
            case QUART -> EasingFunction.QUART;
            case QUINT -> EasingFunction.QUINT;
            case EXPO -> EasingFunction.EXPO;
            case CIRC -> EasingFunction.CIRC;
            case BACK -> EasingFunction.BACK;
            case ELASTIC -> EasingFunction.ELASTIC;
            case BOUNCE -> EasingFunction.BOUNCE;
        };
    }

    public DynamicAnimation dummy(float value) {
        return new DummyAnimation(value);
    }

    public DynamicAnimation build(long duration, float start, float end, EasingFunction function) {
        final DynamicAnimation animation = new DynamicAnimation(function, EasingMode.EASE_IN_OUT, duration, start);
        return animation.setTarget(end);
    }

    public static class DummyAnimation extends DynamicAnimation {
        public DummyAnimation(float target) {
            super(EasingFunction.LINEAR, EasingMode.EASE_IN_OUT, 1, target);
        }
    }
}

package oxy.bascenario.utils.animation;

import lombok.experimental.UtilityClass;
import net.lenni0451.commons.animation.easing.EasingFunction;
import net.lenni0451.commons.animation.easing.EasingMode;
import oxy.bascenario.api.animation.AnimationValue;
import oxy.bascenario.api.effects.Easing;
import oxy.bascenario.api.utils.math.Vec2;
import oxy.bascenario.api.utils.math.Vec3;
import oxy.bascenario.utils.DynamicAnimation;
import oxy.bascenario.utils.MochaUtils;
import team.unnamed.mocha.runtime.Scope;

import java.io.IOException;

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
        final DynamicAnimation animation = new DynamicAnimation(function, EasingMode.EASE_IN_OUT, Math.max(0, duration), start);
        return animation.setTarget(end);
    }

    public DynamicAnimation build(long duration, long time, float start, float end, EasingFunction function) {
        final DynamicAnimation animation = new DynamicAnimation(function, EasingMode.EASE_IN_OUT, Math.max(0, duration), start);
        return animation.setTarget(end, time);
    }

    public static class DummyAnimation extends DynamicAnimation {
        public DummyAnimation(float target) {
            super(EasingFunction.LINEAR, EasingMode.EASE_IN_OUT, 1, target);
        }
    }

    public Vec3 evalVec3(Scope scope, AnimationValue expression) {
        return evalVec3(scope, expression, false);
    }

    public Vec2 evalVec2(Scope scope, AnimationValue expression) {
        return evalVec2(scope, expression, false);
    }

    public Vec3 evalVec3(Scope scope, AnimationValue expression, boolean nullable) {
        try {
            return new Vec3((float) MochaUtils.eval(scope, expression.value()[0]).getAsNumber(), (float) MochaUtils.eval(scope, expression.value()[1]).getAsNumber(), (float) MochaUtils.eval(scope, expression.value()[2]).getAsNumber());
        } catch (IOException ignored) {
            return nullable ? null : new Vec3(0, 0, 0);
        }
    }

    public Vec2 evalVec2(Scope scope, AnimationValue expression, boolean nullable) {
        try {
            return new Vec2((float) MochaUtils.eval(scope, expression.value()[0]).getAsNumber(), (float) MochaUtils.eval(scope, expression.value()[1]).getAsNumber());
        } catch (IOException ignored) {
            return nullable ? null : new Vec2(0, 0);
        }
    }
}

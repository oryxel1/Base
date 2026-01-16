package oxy.bascenario.utils.animation.math;

import oxy.bascenario.utils.animation.DynamicAnimation;
import net.lenni0451.commons.animation.easing.EasingFunction;
import oxy.bascenario.utils.animation.AnimationUtils;
import oxy.bascenario.api.utils.math.Vec3;

public class Vec3Animations {
    private EasingFunction function;
    private DynamicAnimation x, y, z;

    public Vec3Animations() {
        this.function = EasingFunction.LINEAR;
        this.x = AnimationUtils.dummy(0);
        this.y = AnimationUtils.dummy(0);
        this.z = AnimationUtils.dummy(0);
    }

    public Vec3Animations(EasingFunction function, Vec3 vec3) {
        this.function = function;
        this.x = AnimationUtils.dummy(vec3.x());
        this.y = AnimationUtils.dummy(vec3.y());
        this.z = AnimationUtils.dummy(vec3.z());
    }

    public void set(EasingFunction function, Vec3 vec3, long duration) {
        this.function = function;
        if (duration <= 0) {
            this.x = AnimationUtils.dummy(vec3.x());
            this.y = AnimationUtils.dummy(vec3.y());
            this.z = AnimationUtils.dummy(vec3.z());
            return;
        }

        this.x = AnimationUtils.build(duration, this.x.getValue(), vec3.x(), this.function);
        this.y = AnimationUtils.build(duration, this.y.getValue(), vec3.y(), this.function);
        this.z = AnimationUtils.build(duration, this.z.getValue(), vec3.z(), this.function);
    }

    public void add(EasingFunction function, Vec3 vec3, long duration) {
        set(function, new Vec3(vec3.x() + x(), vec3.y() + y(), vec3.z() + z()), duration);
    }

    public float x() {
        return this.x.getValue();
    }

    public float y() {
        return this.y.getValue();
    }

    public float z() {
        return this.z.getValue();
    }
}

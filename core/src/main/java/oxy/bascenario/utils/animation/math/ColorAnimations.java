package oxy.bascenario.utils.animation.math;

import net.lenni0451.commons.animation.DynamicAnimation;
import net.lenni0451.commons.animation.easing.EasingFunction;
import net.lenni0451.commons.color.Color;
import oxy.bascenario.utils.animation.AnimationUtils;

public class ColorAnimations {
    private DynamicAnimation r, g, b, a;

    public ColorAnimations(Color color) {
        this.r = AnimationUtils.dummy(color.getRedF());
        this.g = AnimationUtils.dummy(color.getGreenF());
        this.b = AnimationUtils.dummy(color.getBlueF());
        this.a = AnimationUtils.dummy(color.getAlphaF());
    }

    public void set(Color color, long duration) {
        if (duration <= 0) {
            this.r = AnimationUtils.dummy(color.getRedF());
            this.g = AnimationUtils.dummy(color.getGreenF());
            this.b = AnimationUtils.dummy(color.getBlueF());
            this.a = AnimationUtils.dummy(color.getAlphaF());
            return;
        }

        this.r = AnimationUtils.build(duration, this.r.getValue(), color.getRedF(), EasingFunction.LINEAR);
        this.g = AnimationUtils.build(duration, this.g.getValue(), color.getGreenF(), EasingFunction.LINEAR);
        this.b = AnimationUtils.build(duration, this.b.getValue(), color.getBlueF(), EasingFunction.LINEAR);
        this.a = AnimationUtils.build(duration, this.a.getValue(), color.getAlphaF(), EasingFunction.LINEAR);
    }

    public Color color() {
        return Color.fromRGBAF(r.getValue(), g.getValue(), b.getValue(), a.getValue());
    }

    public float red() {
        return r.getValue();
    }

    public float green() {
        return g.getValue();
    }

    public float blue() {
        return b.getValue();
    }

    public float alpha() {
        return a.getValue();
    }
}

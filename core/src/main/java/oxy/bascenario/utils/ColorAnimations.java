package oxy.bascenario.utils;

import net.lenni0451.commons.animation.DynamicAnimation;
import net.lenni0451.commons.animation.easing.EasingFunction;

import java.awt.*;

public class ColorAnimations {
    private DynamicAnimation r, g, b, a;

    public ColorAnimations(Color color) {
        this.r = AnimationUtils.dummy(color.getRed());
        this.g = AnimationUtils.dummy(color.getGreen());
        this.b = AnimationUtils.dummy(color.getBlue());
        this.a = AnimationUtils.dummy(color.getAlpha());
    }

    public ColorAnimations(net.lenni0451.commons.color.Color color) {
        this.r = AnimationUtils.dummy(color.getRed());
        this.g = AnimationUtils.dummy(color.getGreen());
        this.b = AnimationUtils.dummy(color.getBlue());
        this.a = AnimationUtils.dummy(color.getAlpha());
    }

    public void set(Color color, long duration) {
        this.r = AnimationUtils.build(duration, this.r.getValue(), color.getRed(), EasingFunction.LINEAR);
        this.g = AnimationUtils.build(duration, this.g.getValue(), color.getGreen(), EasingFunction.LINEAR);
        this.b = AnimationUtils.build(duration, this.b.getValue(), color.getBlue(), EasingFunction.LINEAR);
        this.a = AnimationUtils.build(duration, this.a.getValue(), color.getAlpha(), EasingFunction.LINEAR);
    }

    public net.lenni0451.commons.color.Color color() {
        return net.lenni0451.commons.color.Color.fromRGBA((int) r.getValue(), (int) g.getValue(), (int) b.getValue(), (int) a.getValue());
    }

    public float red() {
        return r.getValue() / 255;
    }

    public float green() {
        return g.getValue() / 255;
    }

    public float blue() {
        return b.getValue() / 255;
    }

    public float alpha() {
        return a.getValue() / 255;
    }

}

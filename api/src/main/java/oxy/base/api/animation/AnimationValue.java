package oxy.base.api.animation;

import oxy.base.api.effects.Easing;

public record AnimationValue(String[] value, String duration, Easing easing) {
}

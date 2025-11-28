package oxy.bascenario.api.animation;

import oxy.bascenario.api.effects.Easing;

public record AnimationValue(String[] value, String duration, Easing easing) {
}

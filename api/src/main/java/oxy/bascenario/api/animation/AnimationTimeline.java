package oxy.bascenario.api.animation;

import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@Getter
public class AnimationTimeline {
    private AnimationValue rotation, offset, scale;
}

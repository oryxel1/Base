package oxy.bascenario.api.animation;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Builder(toBuilder = true)
@Getter
public class AnimationTimeline {
    private AnimationValue rotation, offset, scale;
    private String condition;
}

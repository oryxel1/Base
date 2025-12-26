package oxy.bascenario.api.animation;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Builder(toBuilder = true, builderClassName = "Builder")
@Getter
public class AnimationTimeline {
    private AnimationValue rotation, offset, scale, pivot;
    private String condition;
    private Type type;

    public enum Type {
        SET, OFFSET
    }
}

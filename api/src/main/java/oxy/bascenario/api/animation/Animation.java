package oxy.bascenario.api.animation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Getter
public class Animation {
    private final String name;
    private AnimationValue rotation, offset, scale;

    private float maxDuration;
    private final Map<Integer, AnimationTimeline> timelines = new HashMap<>();

    private String initExpression;
}

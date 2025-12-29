package oxy.bascenario.api.event.animation;

import lombok.Builder;
import oxy.bascenario.api.event.api.Event;

@Builder(toBuilder = true, builderClassName = "Builder")
public record SpriteAnimationEvent(int id, float mixTime, String animationName, int trackIndex,
                                   boolean loop) implements Event {
    public SpriteAnimationEvent(int id, float mixTime, String animationName, int trackIndex) {
        this(id, mixTime, animationName, trackIndex, true);
    }
}

package oxy.base.api.event.animation;

import lombok.Builder;
import oxy.base.api.event.api.Event;

@Builder(toBuilder = true, builderClassName = "Builder")
public record StopAnimationEvent(int id, String name) implements Event {
}

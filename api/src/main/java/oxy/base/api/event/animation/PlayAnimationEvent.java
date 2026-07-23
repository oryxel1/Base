package oxy.base.api.event.animation;

import lombok.Builder;
import oxy.base.api.event.api.Event;

@Builder(toBuilder = true, builderClassName = "Builder")
public record PlayAnimationEvent(int id, String name, boolean loop) implements Event {
}

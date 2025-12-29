package oxy.bascenario.api.event.animation;

import lombok.Builder;
import oxy.bascenario.api.event.api.Event;

@Builder(toBuilder = true, builderClassName = "Builder")
public record PlayAnimationEvent(int id, String name, boolean loop) implements Event {
}

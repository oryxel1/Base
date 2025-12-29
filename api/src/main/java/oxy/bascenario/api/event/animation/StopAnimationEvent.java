package oxy.bascenario.api.event.animation;

import lombok.Builder;
import oxy.bascenario.api.event.api.Event;

@Builder(toBuilder = true, builderClassName = "Builder")
public record StopAnimationEvent(int id, String name) implements Event {
}

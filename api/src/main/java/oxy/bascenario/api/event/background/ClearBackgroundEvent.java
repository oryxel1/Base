package oxy.bascenario.api.event.background;

import lombok.Builder;
import oxy.bascenario.api.event.api.Event;

@Builder(toBuilder = true, builderClassName = "Builder")
public record ClearBackgroundEvent(int duration) implements Event {
}

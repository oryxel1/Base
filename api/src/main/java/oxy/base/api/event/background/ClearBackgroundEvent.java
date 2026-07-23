package oxy.base.api.event.background;

import lombok.Builder;
import oxy.base.api.event.api.Event;

@Builder(toBuilder = true, builderClassName = "Builder")
public record ClearBackgroundEvent(int duration) implements Event {
}

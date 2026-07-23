package oxy.base.api.event;

import lombok.Builder;
import oxy.base.api.event.api.Event;

@Builder(toBuilder = true, builderClassName = "Builder")
public record LockClickEvent(boolean lock) implements Event {
}

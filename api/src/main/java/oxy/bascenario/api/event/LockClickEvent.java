package oxy.bascenario.api.event;

import lombok.Builder;
import oxy.bascenario.api.event.api.Event;

@Builder(toBuilder = true, builderClassName = "Builder")
public record LockClickEvent(boolean lock) implements Event {
}

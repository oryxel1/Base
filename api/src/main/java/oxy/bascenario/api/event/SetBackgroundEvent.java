package oxy.bascenario.api.event;

import lombok.Builder;
import oxy.bascenario.api.event.api.Event;
import oxy.bascenario.api.utils.FileInfo;

@Builder(toBuilder = true, builderClassName = "Builder")
public record SetBackgroundEvent(FileInfo background) implements Event {
}

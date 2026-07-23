package oxy.base.api.event.background;

import lombok.Builder;
import oxy.base.api.event.api.Event;
import oxy.base.api.utils.FileInfo;

@Builder(toBuilder = true, builderClassName = "Builder")
public record SetBackgroundEvent(FileInfo background, int duration) implements Event {
}

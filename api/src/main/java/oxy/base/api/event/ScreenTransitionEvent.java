package oxy.base.api.event;

import lombok.Builder;
import oxy.base.api.effects.TransitionType;
import oxy.base.api.event.api.Event;
import oxy.base.api.utils.FileInfo;

@Builder(toBuilder = true, builderClassName = "Builder")
public record ScreenTransitionEvent(FileInfo background, TransitionType type, int waitDuration, int outDuration, int inDuration) implements Event {
}

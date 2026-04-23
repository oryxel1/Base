package oxy.bascenario.api.event;

import lombok.Builder;
import oxy.bascenario.api.effects.TransitionType;
import oxy.bascenario.api.event.api.Event;
import oxy.bascenario.api.utils.FileInfo;

@Builder(toBuilder = true, builderClassName = "Builder")
public record ScreenTransitionEvent(FileInfo background, TransitionType type, int waitDuration, int outDuration, int inDuration) implements Event {
}

package oxy.base.api.event.dialogue;

import lombok.Builder;
import oxy.base.api.event.api.Event;

@Builder(toBuilder = true, builderClassName = "Builder")
public record RedirectDialogueEvent(int index) implements Event {
}

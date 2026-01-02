package oxy.bascenario.api.event.dialogue;

import lombok.Builder;
import oxy.bascenario.api.event.api.Event;

@Builder(toBuilder = true, builderClassName = "Builder")
public record RedirectDialogueEvent(int index) implements Event {
}

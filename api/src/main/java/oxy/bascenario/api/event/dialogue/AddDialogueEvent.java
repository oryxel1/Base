package oxy.bascenario.api.event.dialogue;

import lombok.Builder;
import oxy.bascenario.api.render.elements.Dialogue;
import oxy.bascenario.api.event.api.Event;

@Builder(toBuilder = true, builderClassName = "Builder")
public record AddDialogueEvent(int index, Dialogue... dialogues) implements Event {
}

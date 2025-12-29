package oxy.bascenario.api.event.dialogue;

import lombok.Builder;
import oxy.bascenario.api.render.elements.Dialogue;
import oxy.bascenario.api.event.api.Event;

@Builder(toBuilder = true, builderClassName = "Builder")
public record StartDialogueEvent(int index, String name, String association, boolean background, Dialogue... dialogues) implements Event {
    public StartDialogueEvent(int index, String name, String association, Dialogue... dialogues) {
        this(index, name, association, true, dialogues);
    }
}

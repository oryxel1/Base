package oxy.bascenario.api.event.dialogue;

import lombok.Builder;
import oxy.bascenario.api.render.elements.Dialogue;
import oxy.bascenario.api.event.api.Event;

import java.util.Arrays;
import java.util.Objects;

@Builder(toBuilder = true, builderClassName = "Builder")
public record AddDialogueEvent(int index, Dialogue... dialogues) implements Event {
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AddDialogueEvent that = (AddDialogueEvent) o;
        return index == that.index && Objects.deepEquals(dialogues, that.dialogues);
    }
}

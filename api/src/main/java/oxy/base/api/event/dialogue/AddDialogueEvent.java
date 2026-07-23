package oxy.base.api.event.dialogue;

import lombok.Builder;
import oxy.base.api.render.elements.Dialogue;
import oxy.base.api.event.api.Event;

import java.util.Objects;

@Builder(toBuilder = true, builderClassName = "Builder")
public record AddDialogueEvent(int index, boolean newLine, Dialogue... dialogues) implements Event {
    public AddDialogueEvent(int index, Dialogue... dialogues) {
        this(index, true, dialogues);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AddDialogueEvent that = (AddDialogueEvent) o;
        return index == that.index && newLine == that.newLine && Objects.deepEquals(dialogues, that.dialogues);
    }
}

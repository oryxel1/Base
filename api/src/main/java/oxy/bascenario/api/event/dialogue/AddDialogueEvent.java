package oxy.bascenario.api.event.dialogue;

import lombok.Builder;
import lombok.Getter;
import oxy.bascenario.api.render.elements.Dialogue;
import oxy.bascenario.api.event.api.Event;

@Builder(toBuilder = true)
@Getter
public class AddDialogueEvent extends Event<AddDialogueEvent> {
    private final int index;
    private final Dialogue[] dialogues;

    public AddDialogueEvent(int index, Dialogue... dialogues) {
        this.index = index;
        this.dialogues = dialogues;
    }

    @Override
    public String type() {
        return "add-dialogue";
    }

    @Override
    public AddDialogueEvent empty() {
        return new AddDialogueEvent(0, (Dialogue) null);
    }
}

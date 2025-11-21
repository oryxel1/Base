package oxy.bascenario.api.event.impl.dialogue;

import lombok.Getter;
import oxy.bascenario.api.elements.Dialogue;
import oxy.bascenario.api.event.Event;

@Getter
public class AddDialogueEvent extends Event<AddDialogueEvent> {
    private final int index;
    private final Dialogue[] dialogues;

    public AddDialogueEvent(int index, Dialogue... dialogues) {
        super(0);
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

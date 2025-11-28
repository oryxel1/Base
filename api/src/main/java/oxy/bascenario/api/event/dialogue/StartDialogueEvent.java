package oxy.bascenario.api.event.dialogue;

import lombok.Getter;
import oxy.bascenario.api.render.elements.Dialogue;
import oxy.bascenario.api.event.api.Event;

@Getter
public class StartDialogueEvent extends Event<StartDialogueEvent> {
    private final int index;
    private final String name, association;
    private final boolean background;
    private final Dialogue[] dialogues;

    public StartDialogueEvent(int index, String name, String association, boolean background, Dialogue... dialogues) {
        super(0);
        this.index = index;
        this.name = name;
        this.association = association;
        this.background = background;
        this.dialogues = dialogues;
    }

    public StartDialogueEvent(int index, String name, String association, Dialogue... dialogues) {
        super(0);
        this.index = index;
        this.name = name;
        this.association = association;
        this.background = true;
        this.dialogues = dialogues;
    }

    @Override
    public String type() {
        return "start-dialogue";
    }

    @Override
    public StartDialogueEvent empty() {
        return new StartDialogueEvent(0, "Test", "Test", (Dialogue) null);
    }
}

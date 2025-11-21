package oxy.bascenario.api.event.impl.dialogue;

import oxy.bascenario.api.event.Event;

public class CloseDialogueEvent extends Event<CloseDialogueEvent> {
    public CloseDialogueEvent() {
        super(0);
    }

    @Override
    public String type() {
        return "close-dialogue";
    }

    @Override
    public CloseDialogueEvent empty() {
        return new CloseDialogueEvent();
    }
}

package oxy.bascenario.api.event.dialogue;

import lombok.Builder;
import oxy.bascenario.api.render.elements.Dialogue;
import oxy.bascenario.api.event.api.Event;
import oxy.bascenario.api.render.elements.text.font.FontType;

@Builder(toBuilder = true, builderClassName = "Builder")
public record StartDialogueEvent(FontType type, int index, String name, String association, boolean background, Dialogue... dialogues) implements Event {
    public StartDialogueEvent(FontType type, int index, String name, String association, Dialogue... dialogues) {
        this(type, index, name, association, true, dialogues);
    }

    public StartDialogueEvent(int index, String name, String association, Dialogue... dialogues) {
        this(FontType.NotoSans, index, name, association, true, dialogues);
    }
}

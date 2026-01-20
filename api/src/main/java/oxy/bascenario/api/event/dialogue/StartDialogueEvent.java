package oxy.bascenario.api.event.dialogue;

import lombok.Builder;
import oxy.bascenario.api.render.elements.Dialogue;
import oxy.bascenario.api.event.api.Event;
import oxy.bascenario.api.render.elements.text.font.FontType;

import java.util.Arrays;
import java.util.Objects;

@Builder(toBuilder = true, builderClassName = "Builder")
public record StartDialogueEvent(FontType type, int index, String name, String association, boolean background, Dialogue... dialogues) implements Event {
    public StartDialogueEvent(FontType type, int index, String name, String association, Dialogue... dialogues) {
        this(type, index, name, association, true, dialogues);
    }

    public StartDialogueEvent(int index, String name, String association, Dialogue... dialogues) {
        this(FontType.NotoSans, index, name, association, true, dialogues);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        StartDialogueEvent that = (StartDialogueEvent) o;
        return index == that.index && background == that.background && Objects.equals(name, that.name) && type == that.type && Objects.equals(association, that.association) && Objects.deepEquals(dialogues, that.dialogues);
    }
}

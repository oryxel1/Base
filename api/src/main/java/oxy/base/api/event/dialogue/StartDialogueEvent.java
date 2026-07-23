package oxy.base.api.event.dialogue;

import lombok.Builder;
import oxy.base.api.event.dialogue.enums.TextOffset;
import oxy.base.api.render.elements.Dialogue;
import oxy.base.api.event.api.Event;
import oxy.base.api.render.elements.text.font.FontType;

import java.util.Objects;

@Builder(toBuilder = true, builderClassName = "Builder")
public record StartDialogueEvent(TextOffset offset, FontType type, int index, String name, String association, boolean background, Dialogue... dialogues) implements Event {
    public StartDialogueEvent(TextOffset offset, FontType type, int index, String name, String association, Dialogue... dialogues) {
        this(offset, type, index, name, association, true, dialogues);
    }

    public StartDialogueEvent(FontType type, int index, String name, String association, boolean background, Dialogue... dialogues) {
        this(TextOffset.LEFT, type, index, name, association, background, dialogues);
    }

    public StartDialogueEvent(FontType type, int index, String name, String association, Dialogue... dialogues) {
        this(TextOffset.LEFT, type, index, name, association, true, dialogues);
    }

    public StartDialogueEvent(int index, String name, String association, Dialogue... dialogues) {
        this(TextOffset.LEFT, FontType.NotoSans, index, name, association, true, dialogues);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        StartDialogueEvent that = (StartDialogueEvent) o;
        return offset.equals(that.offset) && index == that.index && background == that.background && Objects.equals(name, that.name) && type == that.type && Objects.equals(association, that.association) && Objects.deepEquals(dialogues, that.dialogues);
    }
}

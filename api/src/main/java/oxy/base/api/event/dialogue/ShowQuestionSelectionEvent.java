package oxy.base.api.event.dialogue;

import lombok.Builder;
import oxy.base.api.event.api.Event;
import oxy.base.api.render.elements.text.font.FontType;

import java.util.List;
import java.util.Objects;

@Builder(toBuilder = true, builderClassName = "Builder")
public record ShowQuestionSelectionEvent(FontType type, String question, List<Answer> answers) implements Event {
    public record Answer(int dialogueIndex, String answer, boolean grayedOut) {
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ShowQuestionSelectionEvent that = (ShowQuestionSelectionEvent) o;
        if (!(type == that.type && Objects.equals(question, that.question)) || answers.size() != that.answers.size()) {
            return false;
        }

        int i = 0;
        for (final Answer answer : answers) {
            if (!that.answers().get(i).equals(answer)) {
                return false;
            }
            i++;
        }

        return true;
    }

}

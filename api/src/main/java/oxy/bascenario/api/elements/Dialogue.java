package oxy.bascenario.api.elements;

import lombok.Getter;
import net.lenni0451.commons.color.Color;
import oxy.bascenario.api.elements.text.FontType;
import oxy.bascenario.api.elements.text.Text;
import oxy.bascenario.api.elements.text.TextSegment;

import java.util.ArrayList;
import java.util.List;

// Yep there are no name, associations, etc... I want this to be a separate thing.
// So for example, a new dialogue line could be added after 600ms for eg, you will see what I mean.
@Getter
public class Dialogue {
    public static long MS_PER_WORD = 38L;

    private Text dialogue;
    private float playSpeed;

    public static Builder builder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return builder().dialogue(this.dialogue).playSpeed(this.playSpeed);
    }

    public static final class Builder {
        private Text dialogue = new Text(new ArrayList<>(), 42);
        private float playSpeed = 1;

        private Builder() {}

        public Text dialogue() {
            return dialogue;
        }

        public Builder dialogue(Text dialogue) {
            this.dialogue = dialogue;
            return this;
        }

        public Builder add(List<TextSegment> dialogue) {
            this.dialogue.segments().addAll(dialogue);
            return this;
        }

        public Builder add(String dialogue) {
            this.add(TextSegment.builder().text(dialogue).build());
            return this;
        }

        public Builder add(String dialogue, FontType type) {
            this.add(TextSegment.builder().text(dialogue).type(type).build());
            return this;
        }

        public Builder add(String dialogue, FontType type, Color color) {
            this.add(TextSegment.builder().text(dialogue).type(type).color(color).build());
            return this;
        }

        public Builder add(String dialogue, Color color) {
            this.add(TextSegment.builder().text(dialogue).color(color).build());
            return this;
        }

        public Builder add(TextSegment segment) {
            this.dialogue.segments().add(segment);
            return this;
        }

        public float playSpeed() {
            return this.playSpeed;
        }

        public Builder playSpeed(float playSpeed) {
            this.playSpeed = playSpeed;
            return this;
        }

        public Dialogue build() {
            Dialogue dialogue = new Dialogue();
            dialogue.dialogue = this.dialogue;
            dialogue.playSpeed = this.playSpeed;
            return dialogue;
        }
    }
}

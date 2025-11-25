package oxy.bascenario.api.elements;

import lombok.Getter;
import net.lenni0451.commons.color.Color;

import java.util.ArrayList;
import java.util.List;

// Yep there are no name, associations, etc... I want this to be a separate thing.
// So for example, a new dialogue line could be added after 600ms for eg, you will see what I mean.
@Getter
public class Dialogue {
    public static long MS_PER_WORD = 38L;

    private List<TextSegment> dialogue;
    private float playSpeed;
    private float scale;

    public static Builder builder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return builder().add(this.dialogue).playSpeed(this.playSpeed).scale(this.scale);
    }

    public static class Builder {
        private List<TextSegment> dialogue = new ArrayList<>();
        private float playSpeed = 1;
        private float scale = 1;

        private Builder() {}

        public List<TextSegment> add() {
            return dialogue;
        }

        public Builder add(List<TextSegment> dialogue) {
            this.dialogue = dialogue;
            return this;
        }

        public Builder add(String dialogue) {
            this.dialogue.add(new TextSegment(dialogue, FontType.REGULAR, Color.WHITE));
            return this;
        }

        public Builder add(String dialogue, FontType type) {
            this.dialogue.add(new TextSegment(dialogue, type, Color.WHITE));
            return this;
        }

        public Builder add(String dialogue, FontType type, Color color) {
            this.dialogue.add(new TextSegment(dialogue, type, color));
            return this;
        }

        public Builder add(String dialogue, Color color) {
            this.add(new TextSegment(dialogue, FontType.REGULAR, color));
            return this;
        }

        public Builder add(TextSegment segment) {
            this.dialogue.add(segment);
            return this;
        }

        public float playSpeed() {
            return this.playSpeed;
        }

        public Builder playSpeed(float playSpeed) {
            this.playSpeed = playSpeed;
            return this;
        }

        public float scale() {
            return scale;
        }

        public Builder scale(float scale) {
            this.scale = scale;
            return this;
        }

        public Dialogue build() {
            Dialogue dialogue = new Dialogue();
            dialogue.dialogue = this.dialogue;
            dialogue.scale = this.scale;
            dialogue.playSpeed = this.playSpeed;
            return dialogue;
        }
    }
}

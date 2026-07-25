package oxy.base.editor.object.values;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.lenni0451.commons.color.Color;
import oxy.base.api.effects.EaseType;
import oxy.base.api.effects.Easing;

import java.util.EnumMap;

@Accessors(fluent = true)
public class KeyframeValue {
    @Getter
    private final EnumMap<ObjectTransform, Keyframe> transformations = new EnumMap<>(ObjectTransform.class);

    public Color color = null;
    public Color overlapColor = null;

    @Accessors(fluent = true)
    @Getter
    @Setter
    public static class Keyframe {
        private float value;
        private Easing easing = null; // Null easing means instant.
        private EaseType easeType = EaseType.EASE_IN;

        public Keyframe(float value) {
            this.value = value;
        }
    }
}

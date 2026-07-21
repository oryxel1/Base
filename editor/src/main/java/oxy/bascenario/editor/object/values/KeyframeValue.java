package oxy.bascenario.editor.object.values;

import lombok.Getter;
import lombok.experimental.Accessors;
import net.lenni0451.commons.color.Color;

import java.util.EnumMap;

@Accessors(fluent = true)
public class KeyframeValue {
    @Getter
    private final EnumMap<ObjectTransform, Object> transformations = new EnumMap<>(ObjectTransform.class);

    public Color color = null;
    public Color overlapColor = null;
}

package oxy.base.editor.object;

import lombok.Getter;
import lombok.experimental.Accessors;
import net.lenni0451.commons.color.Color;
import oxy.base.api.render.RenderLayer;
import oxy.base.editor.object.values.KeyframeValue;
import oxy.base.editor.object.values.ObjectTransform;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

@Accessors(fluent = true)
public class ObjectOrEvent {
    public long start, duration;

    public Object object;
    public RenderLayer layer;
    public boolean requireWait;

    public ObjectOrEvent(long start, long duration, Object object, RenderLayer render, boolean requireWait) {
        this.start = start;
        this.duration = duration;
        this.object = object;
        this.layer = render;
        this.requireWait = requireWait;
    }

    @Getter
    private final EnumMap<ObjectTransform, Float> transformations = new EnumMap<>(ObjectTransform.class);

    public Color color = Color.WHITE;
    public Color overlapColor = Color.TRANSPARENT;

    @Getter
    private final Map<Long, KeyframeValue> keyframes = new HashMap<>();
    public void add(long l, float value, ObjectTransform transform) {
        KeyframeValue kv = keyframes.computeIfAbsent(l, _ -> new KeyframeValue());
        kv.transformations().put(transform, value);
    }
    public void remove(long l, ObjectTransform transform) {
        KeyframeValue kv = keyframes.get(l);
        if (kv == null) {
            return;
        }

        kv.transformations().remove(transform);
        if (kv.transformations().isEmpty() && kv.color == null && kv.overlapColor == null) {
            keyframes.remove(l);
        }
    }
    public float get(long l, ObjectTransform transform) {
        KeyframeValue kv = keyframes.get(l);
        if (kv == null) {
            return transformations.getOrDefault(transform, transform.defaultValue());
        }

        return kv.transformations().getOrDefault(transform, transform.defaultValue());
    }
    public boolean has(long l, ObjectTransform transform) {
        KeyframeValue kv = keyframes.get(l);
        return kv != null && kv.transformations().containsKey(transform);
    }
}

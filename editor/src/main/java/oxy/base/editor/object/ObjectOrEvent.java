package oxy.base.editor.object;

import lombok.Getter;
import lombok.experimental.Accessors;
import net.lenni0451.commons.animation.easing.EasingFunction;
import net.lenni0451.commons.animation.easing.EasingMode;
import net.lenni0451.commons.color.Color;
import net.lenni0451.commons.math.MathUtils;
import oxy.base.api.effects.EaseType;
import oxy.base.api.render.RenderLayer;
import oxy.base.editor.EditorValues;
import oxy.base.editor.object.values.KeyframeValue;
import oxy.base.editor.object.values.ObjectTransform;
import oxy.base.utils.animation.AnimationUtils;

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
    private final EnumMap<ObjectTransform, KeyframeValue.Keyframe> transformations = new EnumMap<>(ObjectTransform.class);

    public Color color = Color.WHITE;
    public Color overlapColor = Color.TRANSPARENT;

    @Getter
    private final Map<Long, KeyframeValue> keyframes = new HashMap<>();
    public void add(long l, float value, ObjectTransform transform, boolean allowNewKeyframe) {
        if (!keyframes.containsKey(l) && !allowNewKeyframe) {
            KeyframeValue.Keyframe v = transformations.putIfAbsent(transform, new KeyframeValue.Keyframe(value));
            if (v != null) {
                v.value(value);
            }
            return;
        }

        KeyframeValue kv = keyframes.computeIfAbsent(l, _ -> new KeyframeValue());

        KeyframeValue.Keyframe v = kv.transformations().putIfAbsent(transform, new KeyframeValue.Keyframe(value));
        if (v != null) {
            v.value(value);
        }
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
            KeyframeValue.Keyframe keyframe = transformations.get(transform);
            // If keyframes are not empty, back track to find the nearest value.
            if (!keyframes.isEmpty()) {
                long closeAhead = Long.MIN_VALUE;
                KeyframeValue closeAheadEntry = null;

                long closeValue = Long.MAX_VALUE;
                KeyframeValue closeEntry = null;
                for (Map.Entry<Long, KeyframeValue> entry : keyframes.entrySet()) {
                    if (entry.getKey() > l) {
                        if (closeAhead == Long.MIN_VALUE || Math.abs(closeAhead - l) > Math.abs(entry.getKey() - l)) {
                            if (entry.getValue().transformations().containsKey(transform)) {
                                closeAhead = entry.getKey();
                                closeAheadEntry = entry.getValue();
                            }
                        }

                        continue;
                    }

                    if (closeValue == Long.MAX_VALUE || Math.abs(closeValue - l) > Math.abs(entry.getKey() - l)) {
                        if (entry.getValue().transformations().containsKey(transform)) {
                            closeValue = entry.getKey();
                            closeEntry = entry.getValue();
                        }
                    }
                }

                if (closeEntry != null) {
                    if (closeAheadEntry != null) {
                        final KeyframeValue.Keyframe behind = closeEntry.transformations().get(transform);
                        final KeyframeValue.Keyframe ahead = closeAheadEntry.transformations().get(transform);

                        float ratio = (l - closeValue) / (float) (closeAhead - closeValue);
                        if (ahead.easing() == null) {
                            return 1 - ratio <= 0.999f ? ahead.value() : behind.value();
                        }

                        EasingFunction func = AnimationUtils.toFunction(ahead.easing());
                        EasingMode mode = EasingMode.values()[ahead.easeType().ordinal()];

                        ratio = mode.call(func, ratio);

                        return behind.value() + (ahead.value() - behind.value()) * ratio;
                    }

                    return closeEntry.transformations().get(transform).value();
                }
            }

            return keyframe == null ? transform.defaultValue() : keyframe.value();
        }

        KeyframeValue.Keyframe keyframe = kv.transformations().get(transform);
        return keyframe == null ? transform.defaultValue() : keyframe.value();
    }
    public boolean has(long l, ObjectTransform transform) {
        KeyframeValue kv = keyframes.get(l);
        return kv != null && kv.transformations().containsKey(transform);
    }
}

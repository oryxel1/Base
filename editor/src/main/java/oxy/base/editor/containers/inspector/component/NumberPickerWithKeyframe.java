package oxy.base.editor.containers.inspector.component;

import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.layout.list.HorizontalListLayout;
import net.lenni0451.rivet.math.Size;
import oxy.base.editor.EditorValues;
import oxy.base.editor.object.ObjectOrEvent;
import oxy.base.editor.object.values.KeyframeValue;
import oxy.base.editor.object.values.ObjectTransform;
import oxy.base.utils.components.KeyframeComponent;
import oxy.base.utils.components.NumberPicker;

import java.util.EnumMap;

public class NumberPickerWithKeyframe extends Container {
    private final ObjectOrEvent object;
    private final ObjectTransform transform;
    private final NumberPicker picker;
    private float timestamp;

    public NumberPickerWithKeyframe(ObjectOrEvent object, ObjectTransform transform, double min, double max, double step) {
        super(new HorizontalListLayout(11, true));
        this.object = object;
        this.transform = transform;

        timestamp = EditorValues.instance().timestamp();

        final KeyframeValue keyframe = object.keyframes().get(EditorValues.instance().timestamp());
        final EnumMap<ObjectTransform, Float> transforms = keyframe == null ? object.transformations() : keyframe.transformations();

        float defaultValue = transform == ObjectTransform.SCALE_X || transform == ObjectTransform.SCALE_Y ? transforms.getOrDefault(transform, 1f) : transforms.getOrDefault(transform, 0f);

        addChild(picker = new NumberPicker(min, max, step, defaultValue), c -> c.minSize(125, 0));
        addChild(new KeyframeComponent(() -> object.has(EditorValues.instance().timestamp(), transform), () -> {
            if (object.has(EditorValues.instance().timestamp(), transform)) {
                object.remove(EditorValues.instance().timestamp(), transform);
                picker.value(object.get(EditorValues.instance().timestamp(), transform));
            } else {
                object.add(EditorValues.instance().timestamp(), (float) picker.value(), transform);
            }
        }));

        picker.valueChangeListener().add(d -> {
            final KeyframeValue kv = object.keyframes().get(EditorValues.instance().timestamp());
            final EnumMap<ObjectTransform, Float> fTransforms = kv == null ? object.transformations() : kv.transformations();

            Float value = fTransforms.get(transform);
            if (value != null || d != transform.defaultValue()) {
                fTransforms.put(transform, d.floatValue());
            }
        });
    }

    @Override
    public void render(Renderer renderer, Size size) {
        super.render(renderer, size);

        long currentTimestamp = EditorValues.instance().timestamp();
        if (timestamp != currentTimestamp) {
            float value = object.get(currentTimestamp, transform);
            timestamp = currentTimestamp;
            picker.value(value);
        }
    }

    @Override
    protected void onComponentAdded() {
        super.onComponentAdded();
        picker.font(rivet().backend().font().derive(14));
    }
}

package oxy.base.editor.containers.dopesheet;

import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.layout.absolute.AbsoluteLayout;
import net.lenni0451.rivet.layout.absolute.AbsoluteOptions;
import net.lenni0451.rivet.math.Size;
import oxy.base.editor.EditorValues;
import oxy.base.editor.object.ObjectOrEvent;
import oxy.base.editor.object.values.KeyframeValue;
import oxy.base.editor.object.values.ObjectTransform;

import java.util.Map;

public class DopeSheetTrack extends Container {
    final ObjectTransform transform;
    public DopeSheetTrack(ObjectTransform transform) {
        super(AbsoluteLayout.INSTANCE);
        this.transform = transform;

        computeChildren();
    }

    @Override
    public void render(Renderer renderer, Size size) {
        super.render(renderer, size);
    }

    @Override
    public Size computeIdealSize(Size constraints) {
        Size idealSize = super.computeIdealSize(new Size(constraints.width(), 20));
        return new Size(idealSize.width(), 20);
    }

    public void computeChildren() {
        clearChildren();

        final ObjectOrEvent object = EditorValues.instance().selectedObject();
        for (Map.Entry<Long, KeyframeValue> entry : object.keyframes().entrySet()) {
            final KeyframeValue value = entry.getValue();

            KeyframeValue.Keyframe keyframe = value.transformations().get(transform);
            if (keyframe == null) {
                continue;
            }

            long time = entry.getKey() -  object.start;

            float newX = timestampToPosition(time, EditorValues.instance().oneMilSecondWidth(), EditorValues.instance().scale());

            final DopeKeyframeComponent component = new DopeKeyframeComponent(keyframe);
            component.layoutOptions(new AbsoluteOptions(newX - 5, 20 / 2f - 5));

            addChild(component);
        }
    }

    private static float timestampToPosition(long timestamp, float size, float scale) {
        return (timestamp * size * scale);
    }
}

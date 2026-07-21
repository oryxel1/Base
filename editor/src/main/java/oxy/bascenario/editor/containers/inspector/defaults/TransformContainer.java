package oxy.bascenario.editor.containers.inspector.defaults;

import lombok.Getter;
import lombok.experimental.Accessors;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.component.impl.Label;
import net.lenni0451.rivet.layout.grid.GridAnchor;
import net.lenni0451.rivet.layout.grid.GridLayout;
import net.lenni0451.rivet.layout.grid.GridOptions;
import net.lenni0451.rivet.math.Padding;
import net.lenni0451.rivet.math.Size;
import net.lenni0451.rivet.text.model.TextOrigin;
import oxy.bascenario.api.utils.math.Vec2;
import oxy.bascenario.api.utils.math.Vec3;
import oxy.bascenario.editor.containers.inspector.DropdownContainer;
import oxy.bascenario.editor.object.ObjectOrEvent;
import oxy.bascenario.editor.object.values.ObjectTransform;
import oxy.bascenario.utils.components.NumberPicker;

@Accessors(fluent = true)
public class TransformContainer extends DropdownContainer {
    @Getter
    private final ObjectOrEvent object;

    private final FixedSizePicker xPosition, yPosition;
    private final FixedSizePicker xScale, yScale;
    private final FixedSizePicker xRotation, yRotation, zRotation;
    private final FixedSizePicker xPivot, yPivot;

    public TransformContainer(ObjectOrEvent object) {
        super("Transforms", new GridLayout(10, 5));

        this.object = object;

        container.addChild(label("Position X"), c -> c.layoutOptions(leftGrid(0, 0)));
        container.addChild(label("Position Y"), c -> c.layoutOptions(leftGrid(0, 1)));

        Vec2 position = (Vec2) object.transformations().getOrDefault(ObjectTransform.POSITION, new Vec2(0, 0));

        container.addChild(xPosition = new FixedSizePicker(-10000.0, 10000.0, 5, position.x()), c -> c.layoutOptions(grid(1, 0)));
        container.addChild(yPosition = new FixedSizePicker(-10000.0, 10000.0, 5, position.y()), c -> c.layoutOptions(grid(1, 1)));

        container.addChild(label("Scale X"), c -> c.layoutOptions(leftGrid(0, 3).withPadding(new Padding(0, 5, 0, 0))));
        container.addChild(label("Scale Y"), c -> c.layoutOptions(leftGrid(0, 4)));

        Vec2 scale = (Vec2) object.transformations().getOrDefault(ObjectTransform.SCALE, new Vec2(1, 1));

        container.addChild(xScale = new FixedSizePicker(0, 1000, 1, scale.x()), c -> c.layoutOptions(grid(1, 3).withPadding(new Padding(0, 5, 0, 0))));
        container.addChild(yScale = new FixedSizePicker(0, 1000, 1, scale.y()), c -> c.layoutOptions(grid(1, 4)));

        container.addChild(label("Rotation X"), c -> c.layoutOptions(leftGrid(0, 5).withPadding(new Padding(0, 5, 0, 0))));
        container.addChild(label("Rotation Y"), c -> c.layoutOptions(leftGrid(0, 6)));
        container.addChild(label("Rotation Z"), c -> c.layoutOptions(leftGrid(0, 7)));

        Vec3 rotation = (Vec3) object.transformations().getOrDefault(ObjectTransform.ROTATE, new Vec3(0, 0, 0));

        container.addChild(xRotation = new FixedSizePicker(-360, 360, 1, rotation.x()), c -> c.layoutOptions(grid(1, 5).withPadding(new Padding(0, 5, 0, 0))));
        container.addChild(yRotation = new FixedSizePicker(-360, 360, 1, rotation.y()), c -> c.layoutOptions(grid(1, 6)));
        container.addChild(zRotation = new FixedSizePicker(-360, 360, 1, rotation.z()), c -> c.layoutOptions(grid(1, 7)));

        container.addChild(label("Pivot X"), c -> c.layoutOptions(leftGrid(0, 8).withPadding(new Padding(0, 5, 0, 0))));
        container.addChild(label("Pivot Y"), c -> c.layoutOptions(leftGrid(0, 9)));

        Vec2 pivot = (Vec2) object.transformations().getOrDefault(ObjectTransform.PIVOT, new Vec2(0, 0));

        container.addChild(xPivot = new FixedSizePicker(-10000.0, 10000.0, 5, pivot.x()), c -> c.layoutOptions(grid(1, 8).withPadding(new Padding(0, 5, 0, 0))));
        container.addChild(yPivot = new FixedSizePicker(-10000.0, 10000.0, 5, pivot.y()), c -> c.layoutOptions(grid(1, 9)));
    }

    @Override
    public void render(Renderer renderer, Size size) {
        super.render(renderer, size);

        Vec2 position = new Vec2((float) xPosition.value(), (float) yPosition.value());
        if (object.transformations().containsKey(ObjectTransform.POSITION) || !object.transformations().containsKey(ObjectTransform.POSITION) && position.x() + position.y() != 0) {
            object.transformations().put(ObjectTransform.POSITION, position);
        }

        Vec2 scale = new Vec2((float) xScale.value(), (float) yScale.value());
        if (object.transformations().containsKey(ObjectTransform.SCALE) || !object.transformations().containsKey(ObjectTransform.SCALE) && scale.x() == 1 && scale.y() == 1) {
            object.transformations().put(ObjectTransform.SCALE, scale);
        }

        Vec3 rotation = new Vec3((float) xRotation.value(), (float) yRotation.value(), (float) zRotation.value());
        if (object.transformations().containsKey(ObjectTransform.ROTATE) || !object.transformations().containsKey(ObjectTransform.ROTATE) && rotation.x() + rotation.y() + rotation.z() != 0) {
            object.transformations().put(ObjectTransform.ROTATE, scale);
        }

        Vec2 pivot = new Vec2((float) xPivot.value(), (float) yPivot.value());
        if (object.transformations().containsKey(ObjectTransform.PIVOT) || !object.transformations().containsKey(ObjectTransform.PIVOT) && pivot.x() + pivot.y() != 0) {
            object.transformations().put(ObjectTransform.PIVOT, pivot);
        }
    }

    private static class FixedSizePicker extends NumberPicker {
        public FixedSizePicker(double min, double max, double step, double value) {
            super(min, max, step, value);
        }

        @Override
        protected void onComponentAdded() {
            super.onComponentAdded();
            font(rivet().backend().font().derive(14));
        }

        @Override
        public Size computeIdealSize(Size constraints) {
            return super.computeIdealSize(constraints).withWidth(constraints.width() - 60f);
        }
    }

    private GridOptions leftGrid(int column, int row) {
        return new GridOptions(column, row).withAnchor(GridAnchor.LEFT);
    }

    private GridOptions grid(int column, int row) {
        return new GridOptions(column, row);
    }

    private Label label(String string) {
        return new Label(string).scale(0.8f).horizontalOrigin(TextOrigin.Horizontal.VISUAL_LEFT);
    }
}

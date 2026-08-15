package oxy.base.editor.containers.inspector.defaults;

import lombok.Getter;
import lombok.experimental.Accessors;
import net.lenni0451.rivet.component.impl.Label;
import net.lenni0451.rivet.layout.grid.GridAnchor;
import net.lenni0451.rivet.layout.grid.GridLayout;
import net.lenni0451.rivet.layout.grid.GridOptions;
import net.lenni0451.rivet.math.Padding;
import net.lenni0451.rivet.text.model.TextOrigin;
import oxy.base.editor.containers.inspector.DropdownContainer;
import oxy.base.editor.containers.inspector.component.NumberPickerWithKeyframe;
import oxy.base.editor.object.ObjectOrEvent;
import oxy.base.editor.object.values.ObjectTransform;

@Accessors(fluent = true)
public class TransformContainer extends DropdownContainer {
    @Getter
    private final ObjectOrEvent object;

    public TransformContainer(ObjectOrEvent object) {
        super("Transforms", new GridLayout(10, 5));

        this.object = object;

        // Position

        container.add(label("Position X"), c -> c.layoutOptions(leftGrid(0, 0)));
        container.add(label("Position Y"), c -> c.layoutOptions(leftGrid(0, 1)));

        container.add(new NumberPickerWithKeyframe(object, ObjectTransform.POSITION_X, -10000.0, 10000.0, 5), c -> c.layoutOptions(grid(1, 0)));
        container.add(new NumberPickerWithKeyframe(object, ObjectTransform.POSITION_Y, -10000.0, 10000.0, 5), c -> c.layoutOptions(grid(1, 1)));

        // Scale

        container.add(label("Scale X"), c -> c.layoutOptions(leftGrid(0, 3).withPadding(new Padding(0, 5, 0, 0))));
        container.add(label("Scale Y"), c -> c.layoutOptions(leftGrid(0, 4)));

        container.add(new NumberPickerWithKeyframe(object, ObjectTransform.SCALE_X, 0, 1000, 1), c -> c.layoutOptions(grid(1, 3).withPadding(new Padding(0, 5, 0, 0))));
        container.add(new NumberPickerWithKeyframe(object, ObjectTransform.SCALE_Y, 0, 1000, 1), c -> c.layoutOptions(grid(1, 4)));

        // Rotation

        container.add(label("Rotation X"), c -> c.layoutOptions(leftGrid(0, 5).withPadding(new Padding(0, 5, 0, 0))));
        container.add(label("Rotation Y"), c -> c.layoutOptions(leftGrid(0, 6)));
        container.add(label("Rotation Z"), c -> c.layoutOptions(leftGrid(0, 7)));

        container.add(new NumberPickerWithKeyframe(object, ObjectTransform.ROTATE_X, -360, 360, 1), c -> c.layoutOptions(grid(1, 5).withPadding(new Padding(0, 5, 0, 0))));
        container.add(new NumberPickerWithKeyframe(object, ObjectTransform.ROTATE_Y, -360, 360, 1), c -> c.layoutOptions(grid(1, 6)));
        container.add(new NumberPickerWithKeyframe(object, ObjectTransform.ROTATE_Z, -360, 360, 1), c -> c.layoutOptions(grid(1, 7)));

        // Pivot

        container.add(label("Pivot X"), c -> c.layoutOptions(leftGrid(0, 8).withPadding(new Padding(0, 5, 0, 0))));
        container.add(label("Pivot Y"), c -> c.layoutOptions(leftGrid(0, 9)));

        container.add(new NumberPickerWithKeyframe(object, ObjectTransform.PIVOT_X, -10000.0, 10000.0, 5), c -> c.layoutOptions(grid(1, 8).withPadding(new Padding(0, 5, 0, 0))));
        container.add(new NumberPickerWithKeyframe(object, ObjectTransform.PIVOT_Y, -10000.0, 10000.0, 5), c -> c.layoutOptions(grid(1, 9)));
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

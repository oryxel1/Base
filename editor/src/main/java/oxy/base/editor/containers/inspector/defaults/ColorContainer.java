package oxy.base.editor.containers.inspector.defaults;

import lombok.Getter;
import lombok.experimental.Accessors;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.component.impl.Label;
import net.lenni0451.rivet.layout.list.VerticalListLayout;
import net.lenni0451.rivet.math.Size;
import oxy.base.editor.containers.inspector.DropdownContainer;
import oxy.base.editor.object.ObjectOrEvent;
import oxy.base.utils.components.AdvancedColorPicker;

@Accessors(fluent = true)
public class ColorContainer extends DropdownContainer {
    @Getter
    private final ObjectOrEvent object;

    private final AdvancedColorPicker colorPicker;
    private final AdvancedColorPicker overlayColorPicker;

    public ColorContainer(ObjectOrEvent object) {
        super("Color", new VerticalListLayout(5, false));
        this.object = object;

        container.add(new Label("Color"));
        container.add(colorPicker = new AdvancedColorPicker(object.color));
        container.add(new Label("Overlay Color"));
        container.add(overlayColorPicker = new AdvancedColorPicker(object.overlapColor));
    }

    @Override
    public void renderInternal(Renderer renderer, Size size) {
        super.renderInternal(renderer, size);

        object.color = colorPicker.color();
        object.overlapColor = overlayColorPicker.color();
    }
}

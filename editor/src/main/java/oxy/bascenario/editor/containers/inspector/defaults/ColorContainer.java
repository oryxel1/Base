package oxy.bascenario.editor.containers.inspector.defaults;

import lombok.Getter;
import lombok.experimental.Accessors;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.component.impl.ColorPicker;
import net.lenni0451.rivet.component.impl.Label;
import net.lenni0451.rivet.layout.list.VerticalListLayout;
import net.lenni0451.rivet.math.Size;
import oxy.bascenario.editor.containers.inspector.DropdownContainer;
import oxy.bascenario.editor.object.ObjectOrEvent;

@Accessors(fluent = true)
public class ColorContainer extends DropdownContainer {
    @Getter
    private final ObjectOrEvent object;

    private final ColorPicker colorPicker;
    private final ColorPicker overlayColorPicker;

    public ColorContainer(ObjectOrEvent object) {
        super("Color", new VerticalListLayout(5, false));
        this.object = object;

        container.addChild(new Label("Color"));
        container.addChild(colorPicker = new ColorPicker(object.color));
        container.addChild(new Label("Overlay Color"));
        container.addChild(overlayColorPicker = new ColorPicker(object.overlapColor));
    }

    @Override
    public void render(Renderer renderer, Size size) {
        super.render(renderer, size);

        object.color = colorPicker.color();
        object.overlapColor = overlayColorPicker.color();
    }
}

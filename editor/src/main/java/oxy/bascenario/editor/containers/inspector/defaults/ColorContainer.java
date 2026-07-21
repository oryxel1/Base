package oxy.bascenario.editor.containers.inspector.defaults;

import lombok.Getter;
import lombok.experimental.Accessors;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.component.impl.Label;
import net.lenni0451.rivet.layout.list.VerticalListLayout;
import net.lenni0451.rivet.math.Size;
import oxy.bascenario.editor.containers.inspector.DropdownContainer;
import oxy.bascenario.editor.object.ObjectOrEvent;
import oxy.bascenario.utils.components.AdvancedColorPicker;

@Accessors(fluent = true)
public class ColorContainer extends DropdownContainer {
    @Getter
    private final ObjectOrEvent object;

    private final AdvancedColorPicker colorPicker;
    private final AdvancedColorPicker overlayColorPicker;

    public ColorContainer(ObjectOrEvent object) {
        super("Color", new VerticalListLayout(5, false));
        this.object = object;

        container.addChild(new Label("Color"));
        container.addChild(colorPicker = new AdvancedColorPicker(object.color));
        container.addChild(new Label("Overlay Color"));
        container.addChild(overlayColorPicker = new AdvancedColorPicker(object.overlapColor));

//        container.addChild(new ColorWheelPicker(Color.RED));
    }

    @Override
    public void render(Renderer renderer, Size size) {
        super.render(renderer, size);

//        object.color = colorPicker.color();
//        object.overlapColor = overlayColorPicker.color();
    }
}

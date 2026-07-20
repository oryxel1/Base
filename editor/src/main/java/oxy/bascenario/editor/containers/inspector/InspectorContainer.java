package oxy.bascenario.editor.containers.inspector;

import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.component.Component;
import net.lenni0451.rivet.component.container.*;
import net.lenni0451.rivet.component.impl.Label;
import net.lenni0451.rivet.layout.border.BorderLayout;
import net.lenni0451.rivet.layout.border.BorderPosition;
import net.lenni0451.rivet.layout.list.VerticalListLayout;
import net.lenni0451.rivet.math.Padding;
import net.lenni0451.rivet.math.Size;
import oxy.bascenario.editor.containers.GlobalContainer;
import oxy.bascenario.utils.components.NumberPicker;

public class InspectorContainer extends GlobalContainer.ResizeableContainer {
    private final Container container;

    public InspectorContainer() {
        super(BorderLayout.DEFAULT);

        container = new Container(new VerticalListLayout(2, false));

        addChild(new Component() {
            @Override
            public Size computeIdealSize(Size size) {
                return size.withHeight(35f); // To take space.
            }
        }, c -> c.layoutOptions(BorderPosition.TOP));
        addChild(new PaddedContainer(new Padding(8, 5, 0 , 0), new ScrollContainer(container)), c -> c.layoutOptions(BorderPosition.CENTER));

        container.addChild(new Label("Test")); // Test

        InspectingDropDownContainer testContainer = new InspectingDropDownContainer("Test");

        final NumberPicker input = new NumberPicker(0, 100, 50) {
            @Override
            public Size computeIdealSize(Size constraints) {
                return super.computeIdealSize(constraints).withWidth(constraints.width() - 8f);
            }
        };

        testContainer.container().addChild(input);

        for (int i = 0; i < 10; i++) {
            testContainer.container().addChild(new Label("test text"));
        }

        container.addChild(testContainer); // Test
    }

    @Override
    public void render(Renderer renderer, Size size) {
        renderer.fillRoundedRect(0, 0, size.width(), size.height(), 5, Color.fromRGB(40, 40, 40));
        super.render(renderer, size);
        renderer.outlineRoundedRect(0, 0, size.width(), size.height(), 5, 1, Color.fromRGB(53, 53, 53));


    }

    @Override
    public Size computeIdealSize(Size constraints) {
        return constraints.withWidth(this.parent().absoluteBounds().size().width() * relativeScale);
    }
}

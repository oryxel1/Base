package oxy.base.editor.containers.inspector;

import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.component.Component;
import net.lenni0451.rivet.component.container.*;
import net.lenni0451.rivet.layout.border.BorderLayout;
import net.lenni0451.rivet.layout.border.BorderPosition;
import net.lenni0451.rivet.layout.list.VerticalListLayout;
import net.lenni0451.rivet.math.Padding;
import net.lenni0451.rivet.math.Size;
import net.lenni0451.rivet.text.model.TextOrigin;
import oxy.base.api.event.api.Event;
import oxy.base.editor.EditorValues;
import oxy.base.editor.containers.GlobalContainer;
import oxy.base.editor.containers.inspector.defaults.ColorContainer;
import oxy.base.editor.containers.inspector.defaults.TransformContainer;
import oxy.base.editor.object.ObjectOrEvent;
import oxy.base.utils.NameUtils;

public class InspectorContainer extends GlobalContainer.ResizeableContainer {
    private final Container container;

    private TransformContainer transformContainer;
    private ColorContainer colorContainer;

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
    }

    @Override
    public void render(Renderer renderer, Size size) {
        renderer.fillRoundedRect(0, 0, size.width(), size.height(), 5, Color.fromRGB(40, 40, 40));
        super.render(renderer, size);
        renderer.outlineRoundedRect(0, 0, size.width(), size.height(), 5, 1, Color.fromRGB(53, 53, 53));

        if (EditorValues.instance().selectedObject() == null) {
            renderer.text(rivet().backend().font().shapeText("Nothing here yet!", Color.WHITE), 10, 10, TextOrigin.Horizontal.VISUAL_LEFT, TextOrigin.Vertical.VISUAL_TOP);

            if (transformContainer != null) {
                container.removeChild(transformContainer);
                transformContainer = null;
            }
            if (colorContainer != null) {
                container.removeChild(colorContainer);
                colorContainer = null;
            }
        } else {
            ObjectOrEvent objectOrEvent = EditorValues.instance().selectedObject();
            renderer.text(rivet().backend().font().shapeText("Edit > " + NameUtils.name(objectOrEvent.object), Color.WHITE), 10, 10, TextOrigin.Horizontal.VISUAL_LEFT, TextOrigin.Vertical.VISUAL_TOP);

            if (objectOrEvent.object instanceof Event) {
                return;
            }

            if (transformContainer == null || transformContainer.object() != objectOrEvent) {
                if (transformContainer != null) {
                    container.removeChild(transformContainer);
                }

                transformContainer = new TransformContainer(objectOrEvent);
                container.addChild(transformContainer);
            }

            if (colorContainer == null || colorContainer.object() != objectOrEvent) {
                if (colorContainer != null) {
                    container.removeChild(colorContainer);
                }

                colorContainer = new ColorContainer(objectOrEvent);
                container.addChild(colorContainer);
            }
        }
    }

    @Override
    public Size computeIdealSize(Size constraints) {
        return constraints.withWidth(this.parent().absoluteBounds().size().width() * relativeScale);
    }
}

package oxy.bascenario.editor.containers.track.component;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.component.Component;
import net.lenni0451.rivet.input.keyboard.ModifierKey;
import net.lenni0451.rivet.input.mouse.MouseButton;
import net.lenni0451.rivet.input.mouse.MouseButtonEvent;
import net.lenni0451.rivet.input.mouse.MouseMoveEvent;
import net.lenni0451.rivet.math.Rectangle;
import net.lenni0451.rivet.math.Size;
import net.lenni0451.rivet.text.model.TextOrigin;
import oxy.bascenario.editor.EditorValues;
import oxy.bascenario.editor.containers.track.TrackListContainer;
import oxy.bascenario.editor.drag.SecondaryDragComponent;
import oxy.bascenario.editor.object.ObjectOrEvent;
import oxy.bascenario.utils.Launcher;
import oxy.bascenario.utils.NameUtils;

import java.util.ArrayList;
import java.util.List;

@Accessors(fluent = true)
public class ObjectComponent extends Component {
    public static final Color OBJECT_COLOR = Color.fromRGB(98, 130, 163).darker();

    private final TrackListContainer parent;
    @Getter
    private final ObjectOrEvent object;

    @Setter
    private boolean dragging;

    public ObjectComponent(TrackListContainer parent, ObjectOrEvent object) {
        this.parent = parent;
        this.object = object;

        EditorValues.instance().selectedObject(object);
    }

    @Override
    public void render(Renderer renderer, Size bounds) {
        if (dragging) {
            if (!rivet().dragAndDropManager().isDragging()) {
                dragging = false;
            }
            return;
        }

        renderer.fillRoundedRect(0, 0, bounds.width(), bounds.height(), 5, OBJECT_COLOR);
        renderer.outlineRoundedRect(0, 0, bounds.width(), bounds.height(), 5, parent.selectionManager().isSelected(this) ? 2 : 1, Color.WHITE);

        renderer.text(this.rivet().backend().font().shapeText(NameUtils.name(object.object), Color.WHITE), 12, 12, TextOrigin.Horizontal.VISUAL_LEFT, TextOrigin.Vertical.LOGICAL_TOP);

        this.parent.selectionManager().addOrRemove(this, parent().childBounds(this));
    }

    @Override
    protected boolean onComponentMouseDown(MouseButtonEvent event, Size bounds) {
        EditorValues.instance().selectedObject(object);

        if (this.parent.selectionManager().isSelected(this)) {
            return true;
        }

        if (!event.modifiers().contains(ModifierKey.CONTROL)) {
            this.parent.selectionManager().objects().clear();
        }

        this.parent.selectionManager().objects().add(this);
        return true;
    }

    @Override
    protected boolean onComponentMouseMove(MouseMoveEvent event, Size size) {
        if (!event.buttons().contains(MouseButton.LEFT)) {
            return false;
        }

        if (rivet().dragAndDropManager().isDragging()) {
            return true;
        }

        Rectangle clickedBounds = parent().childBounds(this);

        final List<SecondaryDragComponent.Data> list = new ArrayList<>();
        for (ObjectComponent component : parent.selectionManager().objects()) {
            Rectangle bounds = component.parent().childBounds(component);
            float x = -event.x() + (bounds.x() - clickedBounds.x());
            float y = -event.y();
            if (component.parent() != parent()) {
                y += (component.parent().absoluteBounds().y() - parent().absoluteBounds().y());
            }

            list.add(new SecondaryDragComponent.Data(component, x, y));
            component.dragging(true);
        }

        // Ehhh using mouseX and mouseY from EngineRenderer is a bit hacky here...
        final SecondaryDragComponent dragComponent = new SecondaryDragComponent((float) Launcher.WINDOW.mouseX, (float) Launcher.WINDOW.mouseY, list);

        rivet().dragAndDropManager().startDrag(
                dragComponent,
                dragComponent,
                (float) -Launcher.WINDOW.mouseX, (float) -Launcher.WINDOW.mouseY
        );

        return true;
    }

    @Override
    public Size computeIdealSize(Size constraints) {
        return new Size(EditorValues.instance().oneMilSecondWidth() * object.duration, constraints.height());
    }
}

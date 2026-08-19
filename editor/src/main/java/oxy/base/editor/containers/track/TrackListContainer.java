package oxy.base.editor.containers.track;

import lombok.Getter;
import lombok.experimental.Accessors;
import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.backend.text.ShapedText;
import net.lenni0451.rivet.component.Component;
import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.component.container.ScrollContainer;
import net.lenni0451.rivet.input.keyboard.Key;
import net.lenni0451.rivet.input.keyboard.KeyEvent;
import net.lenni0451.rivet.input.mouse.MouseButton;
import net.lenni0451.rivet.input.mouse.MouseButtonEvent;
import net.lenni0451.rivet.input.mouse.MouseMoveEvent;
import net.lenni0451.rivet.input.mouse.MouseScrollEvent;
import net.lenni0451.rivet.layout.absolute.AbsoluteOptions;
import net.lenni0451.rivet.layout.list.VerticalListLayout;
import net.lenni0451.rivet.math.Size;
import net.lenni0451.rivet.text.model.TextOrigin;
import oxy.base.editor.EditorValues;
import oxy.base.editor.containers.track.component.ObjectComponent;
import oxy.base.editor.containers.track.component.TrackContainer;

@Accessors(fluent = true)
public class TrackListContainer extends ScrollContainer {
    @Getter
    private final Container container;

    @Getter
    private final TrackSelectionManager selectionManager = new TrackSelectionManager();

    public TrackListContainer() {
        super(container = new Container(new VerticalListLayout(3, false)), true, true);
    }

    private float prevWidth;

    @Override
    public void renderInternal(Renderer renderer, Size size) {
        if (selectionManager.objects().isEmpty()) {
            EditorValues.instance().selectedObject(null);
        }

        if (prevWidth != size.width()) {
            recalculateObjectPosition();
            this.requestLayoutRecalculation();
        }
        prevWidth = size.width();

        float y = -scrollY();
        int i = 0;
        while (!(y >= size.height())) {
            if (i % 2 == 0) {
                renderer.fillRect(0, y, size.width(), 60, Color.fromRGB(24, 24, 24));
            }

            y += 60;
            i++;
        }

        drawTimestampLine(renderer, size);

        super.renderInternal(renderer, size);

        this.selectionManager.render(renderer);
        EditorValues.instance().scroll(scrollX());
    }

    @Override
    public Size computeIdealSize(Size constraints) {
        return constraints;
    }

    public void recalculateObjectPosition() {
        for (Component component : container.children()) {
            if (!(component instanceof TrackContainer track)) {
                continue;
            }

            for (Component child : track.children()) {
                if (!(child instanceof ObjectComponent object)) {
                    continue;
                }

                float newX = timestampToPosition(object.object().start, 0, EditorValues.instance().oneMilSecondWidth(), EditorValues.instance().scale());
                child.layoutOptions(new AbsoluteOptions(newX, 0));
            }
        }
    }

    @Override
    protected boolean onMouseMoveInternal(MouseMoveEvent event, Size bounds) {
        if (event.buttons().contains(MouseButton.LEFT)) {
            this.selectionManager.x1(event.x());
            this.selectionManager.y1(event.y());
            this.rivet().focusedComponent(this);
        }

        return super.onMouseMoveInternal(event, bounds);
    }

    @Override
    protected boolean onMouseDownInternal(MouseButtonEvent event, Size bounds) {
        if (!super.onMouseDownInternal(event, bounds) && event.button() == MouseButton.LEFT) {
            this.selectionManager.x(event.x());
            this.selectionManager.y(event.y());

            this.selectionManager.x1(event.x());
            this.selectionManager.y1(event.y());

            this.selectionManager.objects().clear();

            this.rivet().focusedComponent(this);
        }
        return true;
    }

    @Override
    protected boolean onMouseUpInternal(MouseButtonEvent event, Size bounds) {
        if (event.button() == MouseButton.LEFT) {
            this.selectionManager.x(0);
            this.selectionManager.y(0);
        }

        return super.onMouseUpInternal(event, bounds);
    }

    @Override
    protected boolean onKeyUpInternal(KeyEvent event) {
        if (event.key() == Key.DELETE) {
            selectionManager.objects().forEach(component -> {
                ((Container)component.parent()).remove(component);
                if (EditorValues.instance().selectedObject() == component.object()) {
                    EditorValues.instance().selectedObject(null);
                }
            });
            selectionManager().objects().clear();
        }

        return false;
    }

    private static float timestampToPosition(long timestamp, float offsetX, float size, float scale) {
        return offsetX + (timestamp * size * scale);
    }

    @Override
    protected boolean onMouseScrollInternal(MouseScrollEvent event, Size size) {
        return true;
    }

    private void drawTimestampLine(Renderer renderer, Size size) {
        float x = -EditorValues.instance().scroll();
        while (!(x >= size.width())) {
            float currentX = x - 0.5f;

            renderer.fillRect(currentX, 0, 1, size.height(), Color.fromRGB(48, 48, 48));

            x += EditorValues.instance().oneSecondWidth();
        }
    }
}

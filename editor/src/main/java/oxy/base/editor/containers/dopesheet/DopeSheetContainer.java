package oxy.base.editor.containers.dopesheet;

import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.layout.dock.DockLayout;
import net.lenni0451.rivet.layout.dock.DockPosition;
import net.lenni0451.rivet.math.Size;
import oxy.base.editor.EditorValues;
import oxy.base.editor.object.ObjectOrEvent;

public class DopeSheetContainer extends Container {
    public DopeSheetContainer() {
        super(new DockLayout());

        addChild(new DopeSheetTimeSection(), c -> c.layoutOptions(DockPosition.TOP));
        addChild(new DopeSheetTrackList(), c -> c.layoutOptions(DockPosition.CENTER));
    }

    @Override
    public void render(Renderer renderer, Size size) {
        ObjectOrEvent objectOrEvent = EditorValues.instance().selectedObject();
        if (objectOrEvent != null) {
            long timestamp = EditorValues.instance().timestamp();
            if (timestamp < objectOrEvent.start) {
                EditorValues.instance().timestamp(objectOrEvent.start);
            } else if (timestamp > objectOrEvent.start + objectOrEvent.duration) {
                EditorValues.instance().timestamp(objectOrEvent.start + objectOrEvent.duration);
            }
        }

        renderer.fillRect(0, 0, size.width(), size.height(), Color.fromRGB(30, 30, 30));

        super.render(renderer, size);

        if (objectOrEvent != null) {
            drawCursor(renderer, size);
        }
    }


    private static final Color CURSOR_COLOR = Color.fromRGB(71, 114, 179);
    private void drawCursor(Renderer renderer, Size size) {
//        Rectangle bounds = this.childBounds(trackListContainer);

        long time = EditorValues.instance().timestamp() - EditorValues.instance().selectedObject().start;

        final float offsetX = 0;
        final float cursorX = -2.5f + offsetX + time * EditorValues.instance().oneMilSecondWidth() - EditorValues.instance().scroll();

        renderer.componentBounds(0, 0, Float.MAX_VALUE, this.absoluteBounds().height(), () -> {
            renderer.fillRect(cursorX, 0, 5, size.height(), CURSOR_COLOR.withAlphaF(0.8f));
            renderer.outlineRect(cursorX, 0, 5, size.height(), 1, Color.BLACK);

            renderer.fillTriangle(cursorX + 2.5f - 10, 0, cursorX + 2.5f, 10, cursorX + 2.5f + 10, 0, CURSOR_COLOR);
        });
    }

    @Override
    public Size computeIdealSize(Size constraints) {
        return constraints;
    }
}

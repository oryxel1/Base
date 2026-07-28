package oxy.base.editor.containers.dopesheet;

import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.component.container.ScrollContainer;
import net.lenni0451.rivet.layout.list.VerticalListLayout;
import net.lenni0451.rivet.math.Size;
import oxy.base.editor.EditorValues;
import oxy.base.editor.object.values.ObjectTransform;

public class DopeSheetTrackList extends ScrollContainer {
    private final Container container;

    public DopeSheetTrackList() {
        super(container = new Container(new VerticalListLayout()), true, true);

        for (ObjectTransform transform : ObjectTransform.values()) {
            container.addChild(new DopeSheetTrack(transform));
        }
    }

    @Override
    public void render(Renderer renderer, Size size) {
        float y = -scrollY();
        int i = 0;
        while (!(y >= size.height())) {
            if (i % 2 == 0) {
                renderer.fillRect(0, y, size.width(), 20, Color.fromRGB(24, 24, 24));
            }

            y += 20;
            i++;
        }
        drawTimestampLine(renderer, size);

        super.render(renderer, size);
    }

    private void drawTimestampLine(Renderer renderer, Size size) {
        float x = 0;
        while (!(x >= size.width())) {
            float currentX = x - 0.5f;

            renderer.fillRect(currentX, 0, 1, size.height(), Color.fromRGB(48, 48, 48));

            x += EditorValues.instance().oneSecondWidth();
        }
    }
}

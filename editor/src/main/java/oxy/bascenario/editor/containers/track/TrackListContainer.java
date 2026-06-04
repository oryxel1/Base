package oxy.bascenario.editor.containers.track;

import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.backend.text.ShapedText;
import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.component.container.ScrollContainer;
import net.lenni0451.rivet.layout.list.VerticalListLayout;
import net.lenni0451.rivet.math.Rectangle;
import net.lenni0451.rivet.math.Size;
import net.lenni0451.rivet.text.model.TextOrigin;
import oxy.bascenario.editor.containers.TimelineContainer;

public class TrackListContainer extends ScrollContainer {
    private final TimelineContainer parent;
    private final Container container;

    public TrackListContainer(TimelineContainer parent) {
        super(container = new Container(new VerticalListLayout(3, false)));
        this.parent = parent;
    }

    @Override
    public void render(Renderer renderer, Rectangle bounds) {
        renderer.fillRect(0, 0, parent.trackListWidth(), bounds.height(), Color.fromRGB(35, 35, 35));

        if (container.children().isEmpty()) {
            renderer.fillRoundedRect(parent.trackListWidth(), 0, 300, 1/4f * bounds.height(), 5, Color.fromRGB(50, 50, 50));
            renderer.scale(0.4f, () -> {
                ShapedText text = this.rivet().backend().shapeText("Drop anything here to get started.", Color.WHITE);
                renderer.text(text, parent.trackListWidth() + 350 ,1/4f * bounds.height(), TextOrigin.Horizontal.VISUAL_LEFT, TextOrigin.Vertical.VISUAL_TOP);
            });
        }

        super.render(renderer, bounds);
    }

    @Override
    public Size computeIdealSize(Size constraints) {
        return new Size(constraints.width(), constraints.height());
    }
}

package oxy.bascenario.editor.containers.timeline;

import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.component.container.ScrollContainer;
import net.lenni0451.rivet.layout.list.VerticalListLayout;
import net.lenni0451.rivet.math.Rectangle;
import net.lenni0451.rivet.math.Size;
import oxy.bascenario.editor.containers.TimelineContainer;
import oxy.bascenario.editor.containers.track.TrackComponent;

public class TrackListContainer extends ScrollContainer {
    private final TimelineContainer parent;
    public TrackListContainer(TimelineContainer parent) {
        final Container container = new Container(new VerticalListLayout(3, false));
        super(container);
        this.parent = parent;

        // Test!~
        container.addChild(new TrackComponent(parent));
        container.addChild(new TrackComponent(parent));
        container.addChild(new TrackComponent(parent));
        container.addChild(new TrackComponent(parent));
        container.addChild(new TrackComponent(parent));
        container.addChild(new TrackComponent(parent));
        container.addChild(new TrackComponent(parent));
    }

    @Override
    public void render(Renderer renderer, Rectangle bounds) {
        renderer.fillRect(0, 0, parent.trackListWidth(), bounds.height(), Color.fromRGB(35, 35, 35));

        super.render(renderer, bounds);
    }

    @Override
    public Size computeIdealSize(Size constraints) {
        return new Size(constraints.width(), constraints.height());
    }
}

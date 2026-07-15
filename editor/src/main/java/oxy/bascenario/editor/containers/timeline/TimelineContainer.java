package oxy.bascenario.editor.containers.timeline;

import lombok.Setter;
import lombok.experimental.Accessors;
import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.layout.dock.DockLayout;
import net.lenni0451.rivet.layout.dock.DockPosition;
import net.lenni0451.rivet.math.Size;

@Accessors(fluent = true)
public class TimelineContainer extends Container {
    @Setter
    private float relativeScale = 0.4f;

    public TimelineContainer() {
        super(new DockLayout(0));

        this.addChild(new TimelineDockBar(), c -> c.layoutOptions(DockPosition.TOP));
    }

    @Override
    public void render(Renderer renderer, Size size) {
        renderer.fillRoundedRect(0, 0, size.width(), size.height(), 5, Color.fromRGB(48, 48, 48));
        super.render(renderer, size);
    }

    @Override
    public Size computeIdealSize(Size constraints) {
        return constraints.withHeight(constraints.height() * relativeScale);
    }
}

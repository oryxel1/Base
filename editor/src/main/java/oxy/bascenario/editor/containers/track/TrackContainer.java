package oxy.bascenario.editor.containers.track;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.layout.absolute.AbsoluteLayout;
import net.lenni0451.rivet.math.Rectangle;
import net.lenni0451.rivet.math.Size;
import oxy.bascenario.editor.containers.TimelineContainer;

@Accessors(fluent = true)
public class TrackContainer extends Container {
    @Getter
    private final TimelineContainer container;

    @Setter @Getter
    private int index;
    @Getter
    private float height = 60f;

    public TrackContainer(TimelineContainer container) {
        super(AbsoluteLayout.INSTANCE);
        this.container = container;
    }

    @Override
    public void render(Renderer renderer, Rectangle bounds) {
        renderer.fillRoundedRect(0, 0, bounds.width() + 100, bounds.height(), 5, Color.fromRGB(50, 50, 50));
        super.render(renderer, bounds);
    }

    @Override
    public Size computeIdealSize(Size constraints) {
        Size idealSize = super.computeIdealSize(new Size(constraints.width(), height));
        return new Size(Math.max(idealSize.width(), constraints.width()), height);
    }
}

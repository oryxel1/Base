package oxy.bascenario.editor.containers.track.tab;

import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.layout.Layout;
import net.lenni0451.rivet.layout.anchor.AnchorLayout;
import net.lenni0451.rivet.math.Rectangle;
import net.lenni0451.rivet.math.Size;
import net.lenni0451.rivet.text.model.TextOrigin;
import oxy.bascenario.editor.containers.track.TrackContainer;

public class TrackTabContainer extends Container {
    private final TrackContainer container;

    public TrackTabContainer(TrackContainer container) {
        super(AnchorLayout.INSTANCE);
        this.container = container;
    }

    @Override
    public void render(Renderer renderer, Rectangle bounds) {
        renderer.scale(0.5f, () -> renderer.text(this.rivet().backend().shapeText("Track " + container.index(), Color.WHITE), 10, 10, TextOrigin.Horizontal.VISUAL_LEFT, TextOrigin.Vertical.LOGICAL_TOP));

        super.render(renderer, bounds);
    }

    @Override
    public Size computeIdealSize(Size constraints) {
        return new Size(constraints.width(), container.height());
    }
}

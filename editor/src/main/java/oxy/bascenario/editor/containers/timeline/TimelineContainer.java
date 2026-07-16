package oxy.bascenario.editor.containers.timeline;

import lombok.experimental.Accessors;
import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.component.container.DecoratedContainer;
import net.lenni0451.rivet.component.impl.SolidColor;
import net.lenni0451.rivet.layout.dock.DockLayout;
import net.lenni0451.rivet.layout.dock.DockPosition;
import net.lenni0451.rivet.math.Padding;
import net.lenni0451.rivet.math.Size;
import oxy.bascenario.editor.containers.GlobalContainer;
import oxy.bascenario.editor.containers.sequencer.VideoSequencerContainer;

@Accessors(fluent = true)
public class TimelineContainer extends GlobalContainer.ResizeableContainer {
    public TimelineContainer() {
        super(new DockLayout(0));

        this.addChild(new DecoratedContainer(new SolidColor(), new TimelineDockBar()), c -> {
            c.layoutOptions(DockPosition.TOP);
            c.innerPadding(new Padding(8, 3, 0, 0));
        });
        this.addChild(new TimelineTimeControl(), c -> c.layoutOptions(DockPosition.BOTTOM));
        this.addChild(new VideoSequencerContainer(), c -> c.layoutOptions(DockPosition.CENTER));

        relativeScale = 0.34f;
    }

    @Override
    public void render(Renderer renderer, Size size) {
        renderer.fillRoundedRect(0, 0, size.width(), size.height(), 5, Color.fromRGB(48, 48, 48));
        super.render(renderer, size);
        renderer.outlineRoundedRect(0, 0, size.width(), size.height(), 5, 1, Color.fromRGB(59, 59, 59));
    }

    @Override
    public Size computeIdealSize(Size constraints) {
        return constraints.withHeight(constraints.height() * relativeScale);
    }
}

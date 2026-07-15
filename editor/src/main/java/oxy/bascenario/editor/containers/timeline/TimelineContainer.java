package oxy.bascenario.editor.containers.timeline;

import lombok.Setter;
import lombok.experimental.Accessors;
import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.component.container.ComboBox;
import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.component.container.DecoratedContainer;
import net.lenni0451.rivet.component.impl.SolidColor;
import net.lenni0451.rivet.layout.dock.DockLayout;
import net.lenni0451.rivet.layout.dock.DockPosition;
import net.lenni0451.rivet.math.Padding;
import net.lenni0451.rivet.math.Size;
import oxy.bascenario.editor.containers.sequencer.VideoSequencerContainer;

@Accessors(fluent = true)
public class TimelineContainer extends Container {
    @Setter
    private float relativeScale = 0.34f;

    public TimelineContainer() {
        super(new DockLayout(0));

        this.addChild(new DecoratedContainer(new SolidColor(), new TimelineDockBar()), c -> {
            c.layoutOptions(DockPosition.TOP);
            c.innerPadding(new Padding(8, 2, 0, 0));
        });
        this.addChild(new TimelineTimeControl(), c -> c.layoutOptions(DockPosition.BOTTOM));
        this.addChild(new VideoSequencerContainer(), c -> c.layoutOptions(DockPosition.CENTER));
    }

    @Override
    public void render(Renderer renderer, Size size) {
        renderer.outlineRoundedRect(0, 0, size.width(), size.height(), 5, 1, Color.fromRGB(60, 60, 60));
        renderer.fillRoundedRect(0, 0, size.width(), size.height(), 5, Color.fromRGB(48, 48, 48));
        super.render(renderer, size);
    }

    @Override
    public Size computeIdealSize(Size constraints) {
        return constraints.withHeight(constraints.height() * relativeScale);
    }
}

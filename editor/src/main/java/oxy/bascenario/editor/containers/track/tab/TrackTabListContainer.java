package oxy.bascenario.editor.containers.track.tab;

import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.component.Component;
import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.component.container.ScrollContainer;
import net.lenni0451.rivet.layout.border.BorderLayout;
import net.lenni0451.rivet.layout.border.BorderPosition;
import net.lenni0451.rivet.math.Size;

public class TrackTabListContainer extends Container {
    public TrackTabListContainer(Container container) {
        super(BorderLayout.INSTANCE);

        // Just to take space.
        this.addChild(new Component() {
            @Override
            public Size computeIdealSize(Size constraints) {
                return new Size(constraints.width(), 35);
            }
        }, c -> c.layoutOptions(BorderPosition.TOP));

        this.addChild(new ScrollContainer(container), c -> c.layoutOptions(BorderPosition.CENTER));
    }
}

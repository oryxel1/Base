package oxy.bascenario.editor.containers;

import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.layout.dock.DockLayout;
import net.lenni0451.rivet.layout.dock.DockPosition;
import oxy.bascenario.editor.containers.timeline.TimelineContainer;

public class GlobalContainer extends Container {
    public GlobalContainer() {
        super(new DockLayout());

        addChild(new TimelineContainer(), c -> c.layoutOptions(DockPosition.TOP));
    }
}

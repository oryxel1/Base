package oxy.bascenario.editor.containers.timeline;

import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.layout.anchor.AnchorLayout;
import net.lenni0451.rivet.math.Size;

public class TimelineTimeControl extends Container {
    public TimelineTimeControl() {
        super(AnchorLayout.INSTANCE);
    }

    @Override
    public Size computeIdealSize(Size constraints) {
        return constraints.withHeight(25);
    }
}

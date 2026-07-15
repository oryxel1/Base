package oxy.bascenario.editor.containers.timeline;

import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.layout.list.HorizontalListLayout;
import net.lenni0451.rivet.math.Size;

public class TimelineDockBar extends Container {
    public TimelineDockBar() {
        super(new HorizontalListLayout(12, true));
    }

    @Override
    public Size computeIdealSize(Size constraints) {
        return constraints.withHeight(25);
    }
}

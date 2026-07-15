package oxy.bascenario.editor.containers.timeline;

import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.layout.list.HorizontalListLayout;
import net.lenni0451.rivet.math.Size;
import oxy.bascenario.utils.components.TimelineDockExpandButton;

public class TimelineDockBar extends Container {
    public TimelineDockBar() {
        super(new HorizontalListLayout(12, true));

        addChild(new TimelineDockExpandButton());
    }

    @Override
    public Size computeIdealSize(Size constraints) {
        return constraints.withHeight(25);
    }
}

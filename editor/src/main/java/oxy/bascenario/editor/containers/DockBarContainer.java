package oxy.bascenario.editor.containers;

import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.layout.list.HorizontalListLayout;
import net.lenni0451.rivet.math.Size;
import oxy.bascenario.utils.RivetUtil;
import oxy.bascenario.utils.math.Pair;

import java.util.ArrayList;
import java.util.List;

public class DockBarContainer extends Container {
    public DockBarContainer() {
        super(new HorizontalListLayout(15, true));
    }

    @Override
    public Size computeIdealSize(Size constraints) {
        return constraints.withHeight(25);
    }
}

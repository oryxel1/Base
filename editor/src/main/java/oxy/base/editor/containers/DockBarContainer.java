package oxy.base.editor.containers;

import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.layout.list.HorizontalListLayout;
import net.lenni0451.rivet.math.Size;
import oxy.base.utils.RivetUtil;
import oxy.base.utils.math.Pair;

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

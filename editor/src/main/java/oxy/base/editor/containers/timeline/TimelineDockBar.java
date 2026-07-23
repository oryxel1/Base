package oxy.base.editor.containers.timeline;

import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.component.container.*;
import net.lenni0451.rivet.component.impl.Label;
import net.lenni0451.rivet.component.impl.SolidColor;
import net.lenni0451.rivet.layout.grid.GridAnchor;
import net.lenni0451.rivet.layout.grid.GridFill;
import net.lenni0451.rivet.layout.grid.GridOptions;
import net.lenni0451.rivet.layout.list.HorizontalListLayout;
import net.lenni0451.rivet.layout.list.VerticalListLayout;
import net.lenni0451.rivet.math.Padding;
import net.lenni0451.rivet.math.Size;
import net.lenni0451.rivet.text.model.TextOrigin;
import oxy.base.utils.RivetUtil;
import oxy.base.utils.components.TimelineDockExpandButton;
import oxy.base.utils.math.Pair;

import java.util.ArrayList;
import java.util.List;

import static net.lenni0451.rivet.utils.MathUtils.roundMin;

public class TimelineDockBar extends Container {
    public TimelineDockBar() {
        super(new HorizontalListLayout(12, false));

        addChild(new TimelineDockExpandButton());

        {
            final List<Pair<String, Runnable>> options = new ArrayList<>();
//            addChild(RivetUtil.dropdown("Add", 250f, options));
        }
    }

    @Override
    public Size computeIdealSize(Size constraints) {
        return constraints.withHeight(24);
    }
}

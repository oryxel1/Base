package oxy.bascenario.editor.containers.timeline;

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
import oxy.bascenario.utils.components.TimelineDockExpandButton;

import static net.lenni0451.rivet.utils.MathUtils.roundMin;

public class TimelineDockBar extends Container {
    public TimelineDockBar() {
        super(new HorizontalListLayout(12, false));

        addChild(new TimelineDockExpandButton());

        {
            Container container = new Container(new VerticalListLayout(5, true));
            final ComboBox box = new ComboBox("Select", new DecoratedContainer(
                    new SolidColor(Color.fromRGB(24, 24, 24)).cornerRadius(5), container));

            Button button = box.button();
            ((Label)button.child()).scale(0.3f);
            button.inactiveOutlineColor().set(Color.TRANSPARENT);
            button.activeOutlineColor().set(Color.TRANSPARENT);
            button.inactiveColor().set(Color.TRANSPARENT);

            box.arrowSize().set(0f);
            box.layoutOptions(new GridOptions(0, 6).withAnchor(GridAnchor.LEFT).withWeightX(1).withFill(GridFill.HORIZONTAL).withColumnSpan(2));
            addChild(box);

            float textHeight = 11f;
            Padding padding = new Padding(roundMin(textHeight / 3F, 0), roundMin(textHeight / 10F, 0), roundMin(textHeight / 3F, 0), roundMin(textHeight / 10F, 0));
            button.innerPadding().set(padding.withTop(3f));

            container.addChild(new Button("All", c -> {}), b -> {
                ((Label)b.child()).scale(0.3f);
                b.inactiveOutlineColor().set(Color.TRANSPARENT);
                b.activeOutlineColor().set(Color.TRANSPARENT);
                b.inactiveColor().set(Color.TRANSPARENT);
            });
//            container.addChild(new Button("None", c -> {}));
//            container.addChild(new Button("Invert", c -> {}));
        }
    }

    @Override
    public Size computeIdealSize(Size constraints) {
        return constraints.withHeight(24);
    }
}

package oxy.bascenario.editor.containers.inspector;

import lombok.Getter;
import lombok.experimental.Accessors;
import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.component.container.*;
import net.lenni0451.rivet.component.impl.Label;
import net.lenni0451.rivet.component.impl.SolidColor;
import net.lenni0451.rivet.layout.list.VerticalListLayout;
import net.lenni0451.rivet.math.Padding;
import net.lenni0451.rivet.math.Size;
import net.lenni0451.rivet.text.model.TextOrigin;

@Accessors(fluent = true)
public class InspectingDropDownContainer extends DecoratedContainer {
    @Getter
    private final Container container;
    public InspectingDropDownContainer(String name) {
        final Color textColor = Color.fromRGB(184, 184, 184);

        final SolidColor color = new SolidColor(Color.fromRGB(61, 61, 61));
        color.outlineColor(Color.fromRGB(74, 74, 74));
        color.outlineWidth(1f);
        color.cornerRadius(5f);

        final Label header = new Label(name).scale(0.35f);
        header.textColor().set(textColor);
        header.horizontalOrigin(TextOrigin.Horizontal.VISUAL_LEFT);

        final ScrollContainer scrollContainer = new ScrollContainer(container = new Container(new VerticalListLayout(5, false)));
        final PaddedContainer padded = new PaddedContainer(new Padding(8, 5, 0, 0), scrollContainer);

        final CollapsibleContainer collapsibleContainer = new CollapsibleContainer(header, padded);
        super(color, collapsibleContainer);

        collapsibleContainer.arrowSize().set(20f);
        collapsibleContainer.arrowColor().set(textColor);
        collapsibleContainer.arrowWidth().set(1f);
    }

    @Override
    public Size computeIdealSize(Size constraints) {
        Size size = super.computeIdealSize(constraints);
        return size.withWidth(constraints.width() - 8f);
    }
}

package oxy.bascenario.utils;

import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.Rivet;
import net.lenni0451.rivet.component.container.Button;
import net.lenni0451.rivet.component.container.ComboBox;
import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.component.container.DecoratedContainer;
import net.lenni0451.rivet.component.impl.Label;
import net.lenni0451.rivet.component.impl.SolidColor;
import net.lenni0451.rivet.layout.grid.GridAnchor;
import net.lenni0451.rivet.layout.grid.GridFill;
import net.lenni0451.rivet.layout.grid.GridOptions;
import net.lenni0451.rivet.layout.list.VerticalListLayout;
import net.lenni0451.rivet.math.Padding;
import net.lenni0451.rivet.text.model.TextOrigin;
import net.lenni0451.rivet.theme.Theme;
import oxy.bascenario.utils.math.Pair;

import java.util.List;

import static net.lenni0451.rivet.utils.MathUtils.roundMin;

public class RivetUtil {
    public static void setupTheme(Rivet rivet) {
        rivet.theme(new Theme() {
            @Override
            protected void addValues(final Rivet rivet, final Values values) {
                values.put(Theme.SLIDER_THUMB_COLOR, Color.WHITE);
                values.put(Theme.SCROLL_BAR_WIDTH, 6F);
                values.put(Theme.SEPARATOR_COLOR, Color.WHITE);
            }
        });
    }

    public static ComboBox dropdown(String name, float width, List<Pair<String, Runnable>> options) {
        Container container = new Container(new VerticalListLayout(5, true));
        final DecoratedContainer decoratedContainer = new DecoratedContainer(
                new SolidColor(Color.fromRGB(24, 24, 24)).cornerRadius(5).outlineColor(Color.fromRGB(36, 36, 36)).outlineWidth(1f), container);
        final ComboBox box = new ComboBox(name, decoratedContainer);
        decoratedContainer.minSize(width, 0f);

        Button button = box.button();
        ((Label)button.child()).scale(0.75f);
        button.inactiveOutlineColor().set(Color.TRANSPARENT);
        button.activeOutlineColor().set(Color.TRANSPARENT);
        button.inactiveColor().set(Color.TRANSPARENT);

        box.arrowSize().set(0f);
        box.layoutOptions(new GridOptions(0, 6).withAnchor(GridAnchor.LEFT).withWeightX(1).withFill(GridFill.HORIZONTAL).withColumnSpan(2));

        float textHeight = 11f;
        Padding padding = new Padding(roundMin(textHeight / 3F, 0), roundMin(textHeight / 10F, 0), roundMin(textHeight / 3F, 0), roundMin(textHeight / 10F, 0));
        button.innerPadding().set(padding.withTop(3f));

        for (Pair<String, Runnable> option : options) {
            container.addChild(new Button(option.left(), c -> option.right().run()), b -> {
                ((Label)b.child()).scale(0.775f);
                ((Label)b.child()).horizontalOrigin(TextOrigin.Horizontal.VISUAL_LEFT);
                b.inactiveOutlineColor().set(Color.TRANSPARENT);
                b.activeOutlineColor().set(Color.TRANSPARENT);
                b.inactiveColor().set(Color.TRANSPARENT);
            });
        }
//            container.addChild(new Button("None", c -> {}));
//            container.addChild(new Button("Invert", c -> {}));

        return box;
    }
}

package oxy.base.utils;

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
import oxy.base.utils.math.Pair;

import java.util.List;

import static net.lenni0451.rivet.utils.MathUtils.roundMin;

public class RivetUtil {
    public static void setupTheme(Rivet rivet) {
        rivet.theme(new Theme() {
            @Override
            protected void addValues(final Rivet rivet, final Values values) {
                values.put(Slider.THUMB_COLOR, Color.WHITE);
                values.put(ScrollContainer.BAR_WIDTH, 6F);
                values.put(Separator.COLOR, Color.WHITE);
            }
        });
    }
}

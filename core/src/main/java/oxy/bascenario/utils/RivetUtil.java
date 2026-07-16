package oxy.bascenario.utils;

import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.Rivet;
import net.lenni0451.rivet.component.container.Button;
import net.lenni0451.rivet.component.container.ComboBox;
import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.component.impl.Label;
import net.lenni0451.rivet.layout.list.VerticalListLayout;
import net.lenni0451.rivet.theme.Theme;
import oxy.bascenario.utils.math.Pair;

import java.util.List;

public class RivetUtil {
    public static void setupTheme(Rivet rivet) {
        rivet.theme(new Theme() {
            @Override
            protected void addValues(final Rivet rivet, final Values values) {
                values.put(Theme.SLIDER_THUMB_COLOR, Color.WHITE);
            }
        });
    }
}

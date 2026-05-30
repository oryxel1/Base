package oxy.bascenario.util;

import net.lenni0451.rivet.component.container.Button;
import net.lenni0451.rivet.component.container.ComboBox;
import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.component.impl.Label;
import net.lenni0451.rivet.layout.anchor.AnchorLayoutOptions;
import net.lenni0451.rivet.layout.list.VerticalListLayout;
import net.lenni0451.rivet.text.model.TextOrigin;
import oxy.bascenario.utils.math.Pair;

import java.util.List;

public class RivetUtil {
    public static void dropDownList(Container container, String name, AnchorLayoutOptions layout, List<Pair<String, Runnable>> lists) {
        final Container buttons = new Container(new VerticalListLayout(1, true));

        final ComboBox box = new ComboBox(new Label(name).horizontalOrigin(TextOrigin.Horizontal.LOGICAL_LEFT).scale(0.45f), buttons);
        box.arrowSize().set(0f);
        box.layoutOptions(layout);

        for (Pair<String, Runnable> pair : lists) {
            Button button = new Button(new Label(pair.left()).scale(0.45f), c -> {
                box.close();
                pair.right().run();
            });
            button.cornerRadius().set(0f);
            buttons.addChild(button);
        }

        container.addChild(box);
    }
}

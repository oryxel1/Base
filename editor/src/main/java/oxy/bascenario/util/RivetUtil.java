package oxy.bascenario.util;

import net.lenni0451.rivet.Rivet;
import net.lenni0451.rivet.backend.Renderer;
import net.lenni0451.rivet.component.Container;
import net.lenni0451.rivet.component.base.Button;
import net.lenni0451.rivet.component.base.ComboBox;
import net.lenni0451.rivet.component.impl.Label;
import net.lenni0451.rivet.layout.anchor.AnchorLayoutOptions;
import net.lenni0451.rivet.layout.list.VerticalListLayout;
import net.lenni0451.rivet.math.Rectangle;
import oxy.bascenario.utils.math.Pair;

import java.util.List;

public class RivetUtil {
    public static void menuList(Rivet rivet, Container container, String name, AnchorLayoutOptions layout, List<Pair<String, Runnable>> lists) {
        final Container buttons = new Container(rivet, new VerticalListLayout(5, true)) {
            @Override
            public void render(Renderer renderer, Rectangle bounds) {
                renderer.scale(0.45f, () -> super.render(renderer, bounds));
            }
        };

        final ComboBox box = new ComboBox(rivet, name, buttons) {
            @Override
            public void render(Renderer renderer, Rectangle bounds) {
                renderer.scale(0.45f, () -> super.render(renderer, bounds));
            }
        };
        box.arrowSize().set(0f);
        box.layoutOptions(layout);

        for (Pair<String, Runnable> button : lists) {
            buttons.addChild(new Button(rivet, new Label(rivet, button.left()), c -> {
                box.close();
                button.right().run();
            }));
        }

        container.addChild(box);
    }
}

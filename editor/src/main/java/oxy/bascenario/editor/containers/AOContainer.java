package oxy.bascenario.editor.containers;

import lombok.Setter;
import lombok.experimental.Accessors;
import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.layout.absolute.AbsoluteLayout;
import net.lenni0451.rivet.layout.absolute.AbsoluteLayoutOptions;
import net.lenni0451.rivet.math.Rectangle;
import oxy.bascenario.editor.containers.ao.AOTabContainer;
import oxy.bascenario.editor.containers.ao.tab.AoTextTab;

// Object & Actions container
@Accessors(fluent = true)
public class AOContainer extends Container {
    @Setter
    private Tab tab = Tab.Media;

    public AOContainer() {
        super(AbsoluteLayout.INSTANCE);

        this.addChild(new AOTabContainer(this), c -> c.layoutOptions(new AbsoluteLayoutOptions(0, 0)));
        this.addChild(new AoTextTab(), c -> c.layoutOptions(new AbsoluteLayoutOptions(10, 45)));
    }

    @Override
    public void render(Renderer renderer, Rectangle bounds) {
        renderer.fillRect(0, 0, bounds.width(), bounds.height(), Color.fromRGB(35, 35, 35));
        super.render(renderer, bounds);
    }

    public enum Tab {
        Media, Text, Object, Transition, Actions
    }
}

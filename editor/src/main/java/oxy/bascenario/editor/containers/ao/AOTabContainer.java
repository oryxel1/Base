package oxy.bascenario.editor.containers.ao;

import net.lenni0451.rivet.component.Component;
import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.component.container.ScrollContainer;
import net.lenni0451.rivet.layout.list.HorizontalListLayout;
import net.lenni0451.rivet.math.Size;
import oxy.bascenario.editor.containers.AOContainer;
import oxy.bascenario.utils.components.TabButton;

public class AOTabContainer extends ScrollContainer {
    public AOTabContainer(AOContainer parent) {
        final Container container = new Container(new HorizontalListLayout(2, false));
        super(container, true, false);

        for (AOContainer.Tab tab : AOContainer.Tab.values()) {
            add(parent, container, tab);
        }

        this.barWidth().set(0f);
    }

    @Override
    public Size computeIdealSize(Size constraints) {
        return new Size(constraints.width(), 40);
    }

    private void add(AOContainer parent, Container container, AOContainer.Tab targetTab) {
        final TabButton button = new TabButton(targetTab.name(), tb -> {
            parent.tab(targetTab);

            for (Component component : container.children()) {
                if (component instanceof TabButton tb1) {
                    tb1.line(false);
                }
            }
            tb.line(true);
        });
        if (targetTab == AOContainer.Tab.Media) {
            button.line(true);
        }

        container.addChild(button);
    }
}

package oxy.bascenario.editor.containers.ao;

import net.lenni0451.rivet.component.container.Button;
import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.component.container.ScrollContainer;
import net.lenni0451.rivet.component.impl.Label;
import net.lenni0451.rivet.layout.list.HorizontalListLayout;
import net.lenni0451.rivet.math.Size;
import oxy.bascenario.editor.containers.AOContainer;

public class AOTabContainer extends ScrollContainer {
    public AOTabContainer(AOContainer parent) {
        final Container container = new Container(new HorizontalListLayout(2, false));
        super(container, true, false);

        for (AOContainer.Tab tab : AOContainer.Tab.values()) {
            add(parent, container, tab);
        }
    }

    @Override
    public Size computeIdealSize(Size constraints) {
        return new Size(constraints.width(), 40);
    }

    private void add(AOContainer parent, Container container, AOContainer.Tab targetTab) {
        final Label label = new Label(targetTab.name()).scale(0.5f);
        final Button button = new Button(label, ignored -> {});
        button.clickListener().add(ignored -> {
            parent.tab(targetTab);
        });

        container.addChild(button);
    }
}

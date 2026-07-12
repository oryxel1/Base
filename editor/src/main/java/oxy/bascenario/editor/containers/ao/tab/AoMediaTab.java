package oxy.bascenario.editor.containers.ao.tab;

import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.component.container.ScrollContainer;
import net.lenni0451.rivet.layout.list.VerticalListLayout;

public class AoMediaTab extends ScrollContainer {
    public AoMediaTab() {
        super(new Container(new VerticalListLayout(10, false)));
    }
}

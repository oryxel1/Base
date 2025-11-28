package oxy.bascenario.event.impl.element;

import oxy.bascenario.api.event.element.ElementIndexEvent;
import oxy.bascenario.event.base.FunctionEvent;
import oxy.bascenario.screens.ScenarioScreen;
import oxy.bascenario.screens.renderer.element.base.ElementRenderer;

public class FunctionElementIndex extends FunctionEvent<ElementIndexEvent> {
    public FunctionElementIndex(ElementIndexEvent event) {
        super(event);
    }

    @Override
    public void start(ScenarioScreen screen) {
        final ElementRenderer<?> renderer = screen.getElements().remove(this.event.getIndex());
        if (renderer == null) {
            return;
        }

        if (event.isSwap()) {
            final ElementRenderer<?> swap = screen.getElements().get(this.event.getNewIndex());
            if (swap != null) {
                screen.getElements().put(this.event.getIndex(), swap);
            }
        }

        screen.getElements().put(this.event.getNewIndex(), renderer);
    }
}

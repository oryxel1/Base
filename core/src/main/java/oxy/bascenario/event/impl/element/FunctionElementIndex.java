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
    public void run(ScenarioScreen screen) {
        if (event.getSubIndex() != null) {
            final ElementRenderer<?> renderer = screen.getElements().get(this.event.getMainIndex());
            if (renderer == null) {
                return;
            }

            final ElementRenderer<?> subRenderer = renderer.getSubElements().remove(this.event.getSubIndex());
            if (event.isSwap()) {
                final ElementRenderer<?> swap = renderer.getSubElements().get(this.event.getNewIndex());
                if (swap != null) {
                    renderer.getSubElements().put(this.event.getSubIndex(), swap);
                }
            }

            renderer.getSubElements().put(this.event.getNewIndex(), subRenderer);
        } else {
            final ElementRenderer<?> renderer = screen.getElements().remove(this.event.getMainIndex());
            if (renderer == null) {
                return;
            }

            if (event.isSwap()) {
                final ElementRenderer<?> swap = screen.getElements().get(this.event.getNewIndex());
                if (swap != null) {
                    screen.getElements().put(this.event.getSubIndex(), swap);
                }
            }

            screen.getElements().put(this.event.getNewIndex(), renderer);
        }
    }
}

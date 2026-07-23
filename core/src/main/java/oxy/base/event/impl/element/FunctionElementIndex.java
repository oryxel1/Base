package oxy.base.event.impl.element;

import oxy.base.api.event.element.ElementIndexEvent;
import oxy.base.event.base.FunctionEvent;
import oxy.base.screens.ScenarioScreen;
import oxy.base.screens.renderer.element.base.ElementRenderer;

public class FunctionElementIndex extends FunctionEvent<ElementIndexEvent> {
    public FunctionElementIndex(ElementIndexEvent event) {
        super(event);
    }

    @Override
    public void run(ScenarioScreen screen) {
        if (event.subIndex() != null) {
            final ElementRenderer<?> renderer = screen.getElements().get(this.event.mainIndex());
            if (renderer == null) {
                return;
            }

            final ElementRenderer<?> subRenderer = renderer.getSubElements().remove(this.event.subIndex());
            if (event.swap()) {
                final ElementRenderer<?> swap = renderer.getSubElements().get(this.event.newIndex());
                if (swap != null) {
                    renderer.getSubElements().put(this.event.subIndex(), swap);
                }
            }

            renderer.getSubElements().put(this.event.newIndex(), subRenderer);
        } else {
            final ElementRenderer<?> renderer = screen.getElements().remove(this.event.mainIndex());
            if (renderer == null) {
                return;
            }

            if (event.swap()) {
                final ElementRenderer<?> swap = screen.getElements().get(this.event.newIndex());
                if (swap != null) {
                    screen.getElements().put(this.event.subIndex(), swap);
                }
            }

            screen.getElements().put(this.event.newIndex(), renderer);
        }
    }
}

package oxy.base.event.impl.element;

import oxy.base.api.event.element.RemoveElementEvent;
import oxy.base.event.base.FunctionEvent;
import oxy.base.screens.ScenarioScreen;
import oxy.base.screens.renderer.element.base.ElementRenderer;

public class FunctionRemoveElement extends FunctionEvent<RemoveElementEvent> {
    public FunctionRemoveElement(RemoveElementEvent event) {
        super(event);
    }

    @Override
    public void run(ScenarioScreen screen) {
        final ElementRenderer<?> renderer = event.subId() != null ? screen.getElements().get(Math.abs(event.id())) : screen.getElements().remove(Math.abs(event.id()));
        if (renderer == null) {
            return;
        }

        if (event.subId() == null) {
            renderer.dispose();
        } else {
            final ElementRenderer<?> subRenderer = renderer.getSubElements().remove(Math.abs(event.subId()));
            subRenderer.dispose();
        }
    }
}

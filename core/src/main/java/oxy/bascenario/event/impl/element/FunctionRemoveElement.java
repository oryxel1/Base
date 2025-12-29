package oxy.bascenario.event.impl.element;

import oxy.bascenario.api.event.element.RemoveElementEvent;
import oxy.bascenario.event.base.FunctionEvent;
import oxy.bascenario.screens.ScenarioScreen;
import oxy.bascenario.screens.renderer.element.base.ElementRenderer;

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

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
        final ElementRenderer<?> renderer = event.getSubId() != null ? screen.getElements().get(event.getId()) : screen.getElements().remove(event.getId());
        if (renderer == null) {
            return;
        }

        if (event.getSubId() == null) {
            renderer.dispose();
        } else {
            final ElementRenderer<?> subRenderer = renderer.getSubElements().remove(event.getSubId());
            subRenderer.dispose();
        }
    }
}

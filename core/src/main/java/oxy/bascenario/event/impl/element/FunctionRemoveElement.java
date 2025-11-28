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
    public void start(ScenarioScreen screen) {
        final ElementRenderer<?> renderer = screen.getElements().remove(event.getId());
        if (renderer != null) {
            renderer.dispose();
        }
    }
}

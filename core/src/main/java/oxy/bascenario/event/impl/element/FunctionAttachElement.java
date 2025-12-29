package oxy.bascenario.event.impl.element;

import oxy.bascenario.api.event.element.AttachElementEvent;
import oxy.bascenario.event.base.FunctionEvent;
import oxy.bascenario.screens.ScenarioScreen;
import oxy.bascenario.screens.renderer.element.base.ElementRenderer;

public class FunctionAttachElement extends FunctionEvent<AttachElementEvent> {
    public FunctionAttachElement(AttachElementEvent event) {
        super(event);
    }

    @Override
    public void run(ScenarioScreen screen) {
        final ElementRenderer<?> renderer = screen.getElements().get(Math.abs(this.event.id()));
        if (renderer == null) {
            return;
        }

        final ElementRenderer<?> subRenderer = FunctionAddElement.getRenderer(screen.getScenario(), event.element(), null);
        renderer.resize(0, 0); // TODO: Properly do this?
        renderer.getSubElements().put(Math.abs(event.subId()), subRenderer);
    }
}

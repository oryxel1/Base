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
        final ElementRenderer<?> renderer = screen.getElements().get(this.event.getId());
        if (renderer == null) {
            return;
        }

        // It's a bunch of if else yes, who cares anyway, not like it's a mess.
        final ElementRenderer<?> subRenderer = FunctionAddElement.getRenderer(screen.getScenario(), event.getElement(), null);
        renderer.resize(0, 0); // TODO: Properly do this?
        renderer.getSubElements().put(event.getSubId(), subRenderer);
    }
}

package oxy.base.event.impl.element;

import oxy.base.api.event.element.AttachElementEvent;
import oxy.base.event.base.FunctionEvent;
import oxy.base.screens.ScenarioScreen;
import oxy.base.screens.renderer.element.base.ElementRenderer;

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

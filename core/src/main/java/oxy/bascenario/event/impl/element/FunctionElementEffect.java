package oxy.bascenario.event.impl.element;

import oxy.bascenario.api.event.element.ElementEffectEvent;
import oxy.bascenario.event.base.FunctionEvent;
import oxy.bascenario.screens.ScenarioScreen;
import oxy.bascenario.screens.renderer.element.base.ElementRenderer;

public class FunctionElementEffect extends FunctionEvent<ElementEffectEvent> {
    public FunctionElementEffect(ElementEffectEvent event) {
        super(event);
    }

    @Override
    public void run(ScenarioScreen screen) {
        ElementRenderer<?> renderer = screen.getElements().get(this.event.id());
        if (renderer == null) {
            return;
        }

        if (event.subId() != null) {
            renderer = renderer.getSubElements().get(this.event.subId());
            if (renderer == null) {
                return;
            }
        }

        if (event.type() == ElementEffectEvent.Type.REMOVE) {
            renderer.getEffects().remove(event.effect());
        } else {
            renderer.getEffects().put(event.effect(), event.values());
        }
    }
}

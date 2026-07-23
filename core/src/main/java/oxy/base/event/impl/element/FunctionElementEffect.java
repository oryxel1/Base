package oxy.base.event.impl.element;

import oxy.base.api.event.element.ElementEffectEvent;
import oxy.base.event.base.FunctionEvent;
import oxy.base.screens.ScenarioScreen;
import oxy.base.screens.renderer.element.base.ElementRenderer;

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

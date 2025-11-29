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
        ElementRenderer<?> renderer = screen.getElements().get(this.event.getId());
        if (renderer == null) {
            return;
        }

        if (event.getSubId() != null) {
            renderer = renderer.getSubElements().get(this.event.getSubId());
            if (renderer == null) {
                return;
            }
        }

        if (event.getType() == ElementEffectEvent.Type.REMOVE) {
            renderer.getEffects().remove(event.getEffect());
        } else {
            renderer.getEffects().put(event.getEffect(), event.getValues());
        }
    }
}

package oxy.bascenario.event.impl.element.effect;

import com.google.gson.JsonObject;
import oxy.bascenario.api.event.impl.element.effect.OverlayEffectEvent;
import oxy.bascenario.event.base.FunctionEvent;
import oxy.bascenario.screens.ScenarioScreen;
import oxy.bascenario.screens.renderer.base.ElementRenderer;

public class FunctionOverlayEffect extends FunctionEvent<OverlayEffectEvent> {
    public FunctionOverlayEffect(OverlayEffectEvent event) {
        super(event);
    }

    @Override
    public void start(ScenarioScreen screen) {
        final ElementRenderer<?> renderer = screen.getElements().get(this.event.getId());
        if (renderer == null) {
            return;
        }

        if (event.getType() == OverlayEffectEvent.Type.REMOVE) {
            renderer.getEffects().remove(event.getEffect());
        } else {
            renderer.getEffects().add(event.getEffect());
        }
    }

    @Override
    public void serialize(JsonObject serialized) {
    }

    @Override
    public OverlayEffectEvent deserialize(JsonObject serialized) {
        return null; // TODO...
    }
}

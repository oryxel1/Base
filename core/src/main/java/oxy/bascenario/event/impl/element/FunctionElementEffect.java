package oxy.bascenario.event.impl.element;

import com.google.gson.JsonObject;
import oxy.bascenario.api.elements.OverlayEffect;
import oxy.bascenario.api.event.impl.element.ElementEffectEvent;
import oxy.bascenario.event.base.EventFunction;
import oxy.bascenario.screens.ScenarioScreen;
import oxy.bascenario.screens.renderer.base.ElementRenderer;

public class FunctionElementEffect extends EventFunction<ElementEffectEvent> {
    public FunctionElementEffect(ElementEffectEvent event) {
        super(event);
    }

    @Override
    public void start(ScenarioScreen screen) {
        final ElementRenderer<?> renderer = screen.getElements().get(this.event.getId());
        if (renderer == null) {
            return;
        }

        renderer.setEffect(event.getEffect());
    }

    @Override
    public void serialize(JsonObject serialized) {
        serialized.addProperty("id", event.getId());
        serialized.addProperty("effect", event.getEffect().name());
    }

    @Override
    public ElementEffectEvent deserialize(JsonObject serialized) {
        return new ElementEffectEvent(serialized.get("id").getAsInt(), OverlayEffect.valueOf(serialized.get("hologram").getAsString()));
    }
}

package oxy.bascenario.event.impl.element;

import com.google.gson.JsonObject;
import oxy.bascenario.api.event.impl.element.RemoveElementEvent;
import oxy.bascenario.event.base.FunctionEvent;
import oxy.bascenario.screens.ScenarioScreen;
import oxy.bascenario.screens.renderer.base.ElementRenderer;

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

    @Override
    public void serialize(JsonObject serialized) {
        serialized.addProperty("id", event.getId());
    }

    @Override
    public RemoveElementEvent deserialize(JsonObject serialized) {
        return new RemoveElementEvent(serialized.get("id").getAsInt());
    }
}

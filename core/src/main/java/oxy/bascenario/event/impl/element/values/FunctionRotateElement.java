package oxy.bascenario.event.impl.element.values;

import com.google.gson.JsonObject;
import oxy.bascenario.api.event.element.values.RotateElementEvent;
import oxy.bascenario.event.base.FunctionEvent;
import oxy.bascenario.screens.ScenarioScreen;
import oxy.bascenario.screens.renderer.element.base.ElementRenderer;
import oxy.bascenario.utils.animation.AnimationUtils;

public class FunctionRotateElement extends FunctionEvent<RotateElementEvent> {
    public FunctionRotateElement(RotateElementEvent event) {
        super(event);
    }

    @Override
    public void start(ScenarioScreen screen) {
        final ElementRenderer<?> renderer = screen.getElements().get(this.event.getId());
        if (renderer == null) {
            return;
        }

        renderer.getRotation().set(AnimationUtils.toFunction(event.getEasing()), event.getRotation(), event.getDuration());
    }

    @Override
    public void serialize(JsonObject serialized) {
    }

    @Override
    public RotateElementEvent deserialize(JsonObject serialized) {
        return null;
    }
}

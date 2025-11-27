package oxy.bascenario.event.impl.element;

import com.google.gson.JsonObject;
import net.lenni0451.commons.animation.easing.EasingFunction;
import oxy.bascenario.api.event.impl.element.ScaleElementEvent;
import oxy.bascenario.event.base.FunctionEvent;
import oxy.bascenario.screens.ScenarioScreen;
import oxy.bascenario.screens.renderer.base.ElementRenderer;

public class FunctionScaleElement extends FunctionEvent<ScaleElementEvent> {
    public FunctionScaleElement(ScaleElementEvent event) {
        super(event);
    }

    @Override
    public void start(ScenarioScreen screen) {
        final ElementRenderer<?> renderer = screen.getElements().get(event.getId());
        if (renderer == null) {
            return;
        }

        renderer.scale(event.getScale(), event.getDuration(), switch (event.getEasing()) {
            case LINEAR -> EasingFunction.LINEAR;
            case SINE -> EasingFunction.SINE;
            case QUAD -> EasingFunction.QUAD;
            case CUBIC -> EasingFunction.CUBIC;
            case QUART -> EasingFunction.QUART;
            case QUINT -> EasingFunction.QUINT;
            case EXPO -> EasingFunction.EXPO;
            case CIRC -> EasingFunction.CIRC;
            case BACK -> EasingFunction.BACK;
            case ELASTIC -> EasingFunction.ELASTIC;
            case BOUNCE -> EasingFunction.BOUNCE;
        });
    }

    @Override
    public void serialize(JsonObject serialized) {
        // TODO
    }

    @Override
    public ScaleElementEvent deserialize(JsonObject serialized) {
        return null; // TODO
    }
}

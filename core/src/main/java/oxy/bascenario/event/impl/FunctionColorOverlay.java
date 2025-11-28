package oxy.bascenario.event.impl;

import oxy.bascenario.api.event.ColorOverlayEvent;
import oxy.bascenario.event.base.FunctionEvent;
import oxy.bascenario.screens.ScenarioScreen;
import oxy.bascenario.screens.renderer.element.base.ElementRenderer;

public class FunctionColorOverlay extends FunctionEvent<ColorOverlayEvent> {
    public FunctionColorOverlay(ColorOverlayEvent event) {
        super(event);
    }

    @Override
    public void start(ScenarioScreen screen) {
        ElementRenderer<?> renderer = screen.getElements().get(event.getId().isEmpty() ? Integer.MIN_VALUE + event.layer().ordinal() : event.getId().get());
        if (renderer != null) {
            renderer.getColor().set(event.getColor(), Math.max(0, event.getFade().duration()));
        }
    }

}

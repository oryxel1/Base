package oxy.bascenario.event.impl.color;

import oxy.bascenario.api.event.color.ColorOverlayEvent;
import oxy.bascenario.event.base.FunctionEvent;
import oxy.bascenario.screens.ScenarioScreen;
import oxy.bascenario.screens.renderer.element.base.ElementRenderer;

public class FunctionColorOverlay extends FunctionEvent<ColorOverlayEvent> {
    public FunctionColorOverlay(ColorOverlayEvent event) {
        super(event);
    }

    @Override
    public void run(ScenarioScreen screen) {
        ElementRenderer<?> renderer = screen.getElements().get(event.getId().isEmpty() ? Integer.MIN_VALUE + event.getRenderLayer().ordinal() : event.getId().get());
        if (renderer != null) {
            renderer.getOverlayColor().set(event.getColor(), Math.max(0, event.getDuration()));
        }
    }
}

package oxy.base.event.impl.color;

import oxy.base.api.event.color.ColorOverlayEvent;
import oxy.base.event.base.FunctionEvent;
import oxy.base.screens.ScenarioScreen;
import oxy.base.screens.renderer.element.base.ElementRenderer;

public class FunctionColorOverlay extends FunctionEvent<ColorOverlayEvent> {
    public FunctionColorOverlay(ColorOverlayEvent event) {
        super(event);
    }

    @Override
    public void run(ScenarioScreen screen) {
        ElementRenderer<?> renderer = screen.getElements().get(event.id().isEmpty() ? Integer.MIN_VALUE + event.renderLayer().ordinal() : event.id().get());
        if (renderer != null) {
            renderer.getOverlayColor().set(event.color(), Math.max(0, event.duration()));
        }
    }
}

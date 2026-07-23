package oxy.base.event.impl.color;

import oxy.base.api.event.color.SetColorEvent;
import oxy.base.event.base.FunctionEvent;
import oxy.base.screens.ScenarioScreen;
import oxy.base.screens.renderer.element.base.ElementRenderer;

public class FunctionSetColor extends FunctionEvent<SetColorEvent> {
    public FunctionSetColor(SetColorEvent event) {
        super(event);
    }

    @Override
    public void run(ScenarioScreen screen) {
        ElementRenderer<?> renderer = screen.getElements().get(event.id());
        if (renderer != null) {
            renderer.getColor().set(event.color(), Math.max(0, event.duration()));
        }
    }
}

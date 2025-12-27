package oxy.bascenario.event.impl.color;

import oxy.bascenario.api.event.color.SetColorEvent;
import oxy.bascenario.event.base.FunctionEvent;
import oxy.bascenario.screens.ScenarioScreen;
import oxy.bascenario.screens.renderer.element.base.ElementRenderer;

public class FunctionSetColor extends FunctionEvent<SetColorEvent> {
    public FunctionSetColor(SetColorEvent event) {
        super(event);
    }

    @Override
    public void run(ScenarioScreen screen) {
        ElementRenderer<?> renderer = screen.getElements().get(event.getId());
        if (renderer != null) {
            renderer.getColor().set(event.getColor(), Math.max(0, event.getDuration()));
        }
    }
}

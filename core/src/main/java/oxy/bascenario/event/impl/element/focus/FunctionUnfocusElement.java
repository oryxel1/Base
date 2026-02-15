package oxy.bascenario.event.impl.element.focus;

import net.lenni0451.commons.color.Color;
import oxy.bascenario.api.event.element.focus.FocusElementEvent;
import oxy.bascenario.api.event.element.focus.UnfocusElementEvent;
import oxy.bascenario.event.base.FunctionEvent;
import oxy.bascenario.screens.ScenarioScreen;
import oxy.bascenario.screens.renderer.element.ColorOverlayRenderer;
import oxy.bascenario.screens.renderer.element.base.ElementRenderer;

import java.util.Map;

public class FunctionUnfocusElement extends FunctionEvent<UnfocusElementEvent> {
    public FunctionUnfocusElement(UnfocusElementEvent event) {
        super(event);
    }

    @Override
    public void run(ScenarioScreen screen) {
        for (Map.Entry<Integer, ElementRenderer<?>> entry : screen.getElements().entrySet()) {
            if (entry.getValue() instanceof ColorOverlayRenderer) {
                continue;
            }

            entry.getValue().getOverlayColor().set(Color.TRANSPARENT, 0);
        }
    }
}

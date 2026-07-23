package oxy.base.event.impl.element.focus;

import net.lenni0451.commons.color.Color;
import oxy.base.api.event.element.focus.UnfocusElementEvent;
import oxy.base.event.base.FunctionEvent;
import oxy.base.screens.ScenarioScreen;
import oxy.base.screens.renderer.element.ColorOverlayRenderer;
import oxy.base.screens.renderer.element.base.ElementRenderer;

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

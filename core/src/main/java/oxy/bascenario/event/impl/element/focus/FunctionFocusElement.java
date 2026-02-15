package oxy.bascenario.event.impl.element.focus;

import net.lenni0451.commons.color.Color;
import oxy.bascenario.api.event.element.focus.FocusElementEvent;
import oxy.bascenario.event.base.FunctionEvent;
import oxy.bascenario.screens.ScenarioScreen;
import oxy.bascenario.screens.renderer.element.ColorOverlayRenderer;
import oxy.bascenario.screens.renderer.element.base.ElementRenderer;

import java.util.Map;

public class FunctionFocusElement extends FunctionEvent<FocusElementEvent> {
    public FunctionFocusElement(FocusElementEvent event) {
        super(event);
    }

    @Override
    public void run(ScenarioScreen screen) {
        final ElementRenderer<?> renderer = screen.getElements().get(Math.abs(this.event.id()));
        if (renderer == null) {
            return;
        }

        renderer.getOverlayColor().set(Color.TRANSPARENT, 0);
        for (Map.Entry<Integer, ElementRenderer<?>> entry : screen.getElements().entrySet()) {
            if (entry.getValue() instanceof ColorOverlayRenderer) {
                continue;
            }

            if (entry.getKey() == Math.abs(this.event.id())) {
                continue;
            }

            entry.getValue().getOverlayColor().set(Color.BLACK.withAlphaF(0.4f), 0);
        }
    }
}

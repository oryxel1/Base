package oxy.bascenario.event.impl.element.values;

import net.lenni0451.commons.animation.easing.EasingFunction;
import oxy.bascenario.api.event.element.values.PositionElementEvent;
import oxy.bascenario.event.base.FunctionEvent;
import oxy.bascenario.screens.ScenarioScreen;
import oxy.bascenario.screens.renderer.element.base.ElementRenderer;
import oxy.bascenario.utils.animation.AnimationUtils;

public class FunctionPositionElement extends FunctionEvent<PositionElementEvent> {
    public FunctionPositionElement(PositionElementEvent event) {
        super(event);
    }

    @Override
    public void run(ScenarioScreen screen) {
        ElementRenderer<?> renderer = screen.getElements().get(this.event.id());
        if (renderer == null) {
            return;
        }

        if (event.subId() != null) {
            renderer = renderer.getSubElements().get(this.event.subId());
            if (renderer == null) {
                return;
            }
        }

        final EasingFunction function = AnimationUtils.toFunction(event.easing());
        switch (event.type()) {
            case SCALE -> renderer.getScale().set(function, event.vec(), event.duration());
            case POSITION -> renderer.getPosition().set(function, event.vec(), event.duration());
            case OFFSET -> renderer.getOffset().set(function, event.vec(), event.duration());
            case PIVOT -> renderer.getPivot().set(function, event.vec(), event.duration());
        }
    }
}

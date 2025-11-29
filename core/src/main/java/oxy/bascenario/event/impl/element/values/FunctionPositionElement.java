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
        ElementRenderer<?> renderer = screen.getElements().get(this.event.getId());
        if (renderer == null) {
            return;
        }

        if (event.getSubId() != null) {
            renderer = renderer.getSubElements().get(this.event.getSubId());
            if (renderer == null) {
                return;
            }
        }

        final EasingFunction function = AnimationUtils.toFunction(event.getEasing());
        switch (event.getType()) {
            case SCALE -> renderer.getScale().set(function, event.getVec(), event.getDuration());
            case POSITION -> renderer.getPosition().set(function, event.getVec(), event.getDuration());
            case OFFSET -> renderer.getOffset().set(function, event.getVec(), event.getDuration());
        }
    }
}

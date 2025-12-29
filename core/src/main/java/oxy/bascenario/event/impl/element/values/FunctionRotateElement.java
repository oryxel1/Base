package oxy.bascenario.event.impl.element.values;

import oxy.bascenario.api.event.element.values.RotateElementEvent;
import oxy.bascenario.event.base.FunctionEvent;
import oxy.bascenario.screens.ScenarioScreen;
import oxy.bascenario.screens.renderer.element.base.ElementRenderer;
import oxy.bascenario.utils.animation.AnimationUtils;

public class FunctionRotateElement extends FunctionEvent<RotateElementEvent> {
    public FunctionRotateElement(RotateElementEvent event) {
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

        renderer.getRotation().set(AnimationUtils.toFunction(event.easing()), event.rotation(), event.duration());
    }
}

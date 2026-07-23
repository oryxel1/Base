package oxy.base.event.impl.element.values;

import oxy.base.api.event.element.values.RotateElementEvent;
import oxy.base.event.base.FunctionEvent;
import oxy.base.screens.ScenarioScreen;
import oxy.base.screens.renderer.element.base.ElementRenderer;
import oxy.base.utils.animation.AnimationUtils;

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

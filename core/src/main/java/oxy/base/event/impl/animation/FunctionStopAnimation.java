package oxy.base.event.impl.animation;

import oxy.base.api.event.animation.StopAnimationEvent;
import oxy.base.event.base.FunctionEvent;
import oxy.base.screens.ScenarioScreen;
import oxy.base.screens.renderer.element.base.ElementRenderer;

public class FunctionStopAnimation extends FunctionEvent<StopAnimationEvent> {
    public FunctionStopAnimation(StopAnimationEvent event) {
        super(event);
    }

    @Override
    public void run(ScenarioScreen screen) {
        final ElementRenderer<?> renderer = screen.getElements().get(event.id());
        if (renderer == null) {
            return;
        }

        renderer.getAnimations().remove(event.name());
    }
}

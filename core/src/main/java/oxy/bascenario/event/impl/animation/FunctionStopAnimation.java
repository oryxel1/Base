package oxy.bascenario.event.impl.animation;

import oxy.bascenario.api.event.animation.StopAnimationEvent;
import oxy.bascenario.event.base.FunctionEvent;
import oxy.bascenario.screens.ScenarioScreen;
import oxy.bascenario.screens.renderer.element.base.ElementRenderer;

public class FunctionStopAnimation extends FunctionEvent<StopAnimationEvent> {
    public FunctionStopAnimation(StopAnimationEvent event) {
        super(event);
    }

    @Override
    public void run(ScenarioScreen screen) {
        final ElementRenderer<?> renderer = screen.getElements().get(event.getId());
        if (renderer == null) {
            return;
        }

        renderer.getAnimations().remove(event.getName());
    }
}

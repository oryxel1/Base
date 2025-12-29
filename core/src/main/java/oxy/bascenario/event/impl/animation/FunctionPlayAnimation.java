package oxy.bascenario.event.impl.animation;

import oxy.bascenario.Base;
import oxy.bascenario.api.event.animation.PlayAnimationEvent;
import oxy.bascenario.event.base.FunctionEvent;
import oxy.bascenario.screens.ScenarioScreen;
import oxy.bascenario.screens.renderer.AnimationTicker;
import oxy.bascenario.screens.renderer.element.base.ElementRenderer;

public class FunctionPlayAnimation extends FunctionEvent<PlayAnimationEvent> {
    public FunctionPlayAnimation(PlayAnimationEvent event) {
        super(event);
    }

    @Override
    public void run(ScenarioScreen screen) {
        final ElementRenderer<?> renderer = screen.getElements().get(event.id());
        if (renderer == null) {
            return;
        }

        try {
            renderer.getAnimations().put(event.name(), new AnimationTicker(screen, renderer, Base.instance().animationManager().get(event.name()), event.loop()));
        } catch (Exception ignored) {
        }
    }
}

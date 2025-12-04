package oxy.bascenario.event.impl.animation;

import oxy.bascenario.api.event.animation.PlayAnimationEvent;
import oxy.bascenario.event.base.FunctionEvent;
import oxy.bascenario.managers.AnimationManager;
import oxy.bascenario.screens.ScenarioScreen;
import oxy.bascenario.screens.renderer.AnimationTicker;
import oxy.bascenario.screens.renderer.element.base.ElementRenderer;

public class FunctionPlayAnimation extends FunctionEvent<PlayAnimationEvent> {
    public FunctionPlayAnimation(PlayAnimationEvent event) {
        super(event);
    }

    @Override
    public void run(ScenarioScreen screen) {
        final ElementRenderer<?> renderer = screen.getElements().get(event.getId());
        if (renderer == null) {
            return;
        }

        try {
            renderer.getAnimations().put(event.getName(), new AnimationTicker(screen, renderer, AnimationManager.getInstance().get(event.getName()), event.isLoop()));
        } catch (Exception ignored) {
        }
    }
}

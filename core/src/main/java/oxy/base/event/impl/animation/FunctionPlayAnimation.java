package oxy.base.event.impl.animation;

import oxy.base.Base;
import oxy.base.api.event.animation.PlayAnimationEvent;
import oxy.base.event.base.FunctionEvent;
import oxy.base.screens.ScenarioScreen;
import oxy.base.screens.renderer.AnimationTicker;
import oxy.base.screens.renderer.element.base.ElementRenderer;

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

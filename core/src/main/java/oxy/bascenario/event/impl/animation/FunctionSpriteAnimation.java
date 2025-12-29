package oxy.bascenario.event.impl.animation;

import oxy.bascenario.api.event.animation.SpriteAnimationEvent;
import oxy.bascenario.event.base.FunctionEvent;
import oxy.bascenario.screens.ScenarioScreen;
import oxy.bascenario.screens.renderer.element.SpriteRenderer;
import oxy.bascenario.screens.renderer.element.base.ElementRenderer;

public class FunctionSpriteAnimation extends FunctionEvent<SpriteAnimationEvent> {
    public FunctionSpriteAnimation(SpriteAnimationEvent event) {
        super(event);
    }

    @Override
    public void run(ScenarioScreen screen) {
        final ElementRenderer<?> renderer = screen.getElements().get(event.id());
        if (!(renderer instanceof SpriteRenderer spriteRenderer)) {
            return;
        }

        spriteRenderer.play(event.animationName(), event.trackIndex(), event.mixTime(), event.loop());
    }
}

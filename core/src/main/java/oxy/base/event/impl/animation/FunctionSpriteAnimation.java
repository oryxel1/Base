package oxy.base.event.impl.animation;

import oxy.base.api.event.animation.SpriteAnimationEvent;
import oxy.base.event.base.FunctionEvent;
import oxy.base.screens.ScenarioScreen;
import oxy.base.screens.renderer.element.SpriteRenderer;
import oxy.base.screens.renderer.element.base.ElementRenderer;

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

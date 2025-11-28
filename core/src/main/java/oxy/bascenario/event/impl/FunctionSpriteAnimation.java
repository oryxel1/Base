package oxy.bascenario.event.impl;

import oxy.bascenario.api.event.SpriteAnimationEvent;
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
        final ElementRenderer<?> renderer = screen.getElements().get(event.getId());
        if (!(renderer instanceof SpriteRenderer spriteRenderer)) {
            return;
        }

        spriteRenderer.play(event.getAnimationName(), event.getTrackIndex(), event.getMixTime(), event.isLoop());
    }
}

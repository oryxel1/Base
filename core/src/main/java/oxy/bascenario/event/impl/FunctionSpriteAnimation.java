package oxy.bascenario.event.impl;

import com.google.gson.JsonObject;
import oxy.bascenario.api.event.impl.SpriteAnimationEvent;
import oxy.bascenario.event.base.FunctionEvent;
import oxy.bascenario.screens.ScenarioScreen;
import oxy.bascenario.screens.renderer.SpriteRenderer;
import oxy.bascenario.screens.renderer.base.ElementRenderer;

public class FunctionSpriteAnimation extends FunctionEvent<SpriteAnimationEvent> {
    public FunctionSpriteAnimation(SpriteAnimationEvent event) {
        super(event);
    }

    @Override
    public void start(ScenarioScreen screen) {
        final ElementRenderer<?> renderer = screen.getElements().get(event.getId());
        if (!(renderer instanceof SpriteRenderer spriteRenderer)) {
            return;
        }

        spriteRenderer.play(event.getAnimationName(), event.getTrackIndex(), event.getMixTime(), event.isLoop());
    }

    @Override
    public void serialize(JsonObject serialized) {
        // TODO: Implement this.
    }

    @Override
    public SpriteAnimationEvent deserialize(JsonObject serialized) {
        return null; // TODO: Implement this.
    }
}

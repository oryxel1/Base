package oxy.bascenario.api.event.impl.element.effect;

import lombok.Getter;
import oxy.bascenario.api.elements.effect.OverlayEffect;
import oxy.bascenario.api.event.Event;

@Getter
public class OverlayEffectEvent extends Event<OverlayEffectEvent> {
    private final int id;
    private final OverlayEffect effect;
    private final Type type;

    public OverlayEffectEvent(int id, OverlayEffect effect, Type type) {
        super(0);
        this.id = id;
        this.effect = effect;
        this.type = type;
    }

    @Override
    public String type() {
        return "effect-add";
    }

    @Override
    public OverlayEffectEvent empty() {
        return new OverlayEffectEvent(0, OverlayEffect.HOLOGRAM_Y, Type.ADD);
    }

    public enum Type {
        ADD, REMOVE
    }
}

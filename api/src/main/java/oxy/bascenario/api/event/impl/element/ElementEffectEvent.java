package oxy.bascenario.api.event.impl.element;

import lombok.Getter;
import oxy.bascenario.api.elements.OverlayEffect;
import oxy.bascenario.api.event.Event;

@Getter
public class ElementEffectEvent extends Event<ElementEffectEvent> {
    private final int id;
    private final OverlayEffect effect;

    public ElementEffectEvent(int id, OverlayEffect effect) {
        super(0);
        this.id = id;
        this.effect = effect;
    }

    @Override
    public String type() {
        return "element-effect";
    }

    @Override
    public ElementEffectEvent empty() {
        return new ElementEffectEvent(0, OverlayEffect.HOLOGRAM);
    }
}

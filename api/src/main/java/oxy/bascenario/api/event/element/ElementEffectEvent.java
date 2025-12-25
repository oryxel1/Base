package oxy.bascenario.api.event.element;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import oxy.bascenario.api.effects.Effect;
import oxy.bascenario.api.event.api.Event;
import oxy.bascenario.api.render.elements.emoticon.EmoticonType;
import oxy.bascenario.api.utils.math.Axis;

@SuppressWarnings("ALL")
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder(toBuilder = true)
@Getter
public class ElementEffectEvent extends Event<ElementEffectEvent> {
    private final int id;
    private final Integer subId;
    private final Effect effect;
    private final Type type;
    private final Object[] values;

    public ElementEffectEvent(int id, Effect effect, Object... values) {
        this.id = id;
        this.effect = effect;
        this.type = Type.ADD;
        this.values = values;
        this.subId = null;
    }

    public ElementEffectEvent(int id, Effect effect, Type type) {
        this.id = id;
        this.effect = effect;
        this.type = type;
        this.values = type == Type.ADD ? new Object[] {} : null;
        this.subId = null;
    }

    public ElementEffectEvent(int id, int subId, Effect effect, Object... values) {
        this.id = id;
        this.effect = effect;
        this.type = Type.ADD;
        this.values = values;
        this.subId = subId;
    }

    public ElementEffectEvent(int id, int subId, Effect effect, Type type) {
        this.id = id;
        this.effect = effect;
        this.type = type;
        this.values = type == Type.ADD ? new Object[] {} : null;
        this.subId = subId;
    }

    @Override
    public String type() {
        return "element-effect";
    }

    @Override
    public ElementEffectEvent empty() {
        return new ElementEffectEvent(0, Effect.HOLOGRAM, Axis.Y);
    }

    public enum Type {
        ADD, REMOVE;
    }
}

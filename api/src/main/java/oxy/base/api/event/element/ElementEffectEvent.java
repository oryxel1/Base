package oxy.base.api.event.element;

import lombok.Builder;
import oxy.base.api.effects.Effect;
import oxy.base.api.event.api.Event;

@Builder(toBuilder = true, builderClassName = "Builder")
public record ElementEffectEvent(int id, Integer subId, Effect effect, Type type, Object[] values) implements Event {
    public ElementEffectEvent(int id, Effect effect, Object... values) {
        this(id, null, effect, Type.ADD, values);
    }

    public ElementEffectEvent(int id, Effect effect, Type type) {
        this(id, null, effect, type, type == Type.ADD ? new Object[] {} : null);
    }

    public ElementEffectEvent(int id, int subId, Effect effect, Object... values) {
        this(id, subId, effect, Type.ADD, values);
    }

    public enum Type {
        ADD, REMOVE;
    }
}

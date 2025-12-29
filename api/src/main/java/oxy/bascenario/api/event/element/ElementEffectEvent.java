package oxy.bascenario.api.event.element;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import oxy.bascenario.api.effects.Effect;
import oxy.bascenario.api.event.api.Event;
import oxy.bascenario.api.utils.math.Axis;

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

    public ElementEffectEvent(int id, int subId, Effect effect, Type type) {
        this(id, subId, effect, type, type == Type.ADD ? new Object[] {} : null);
    }

    public enum Type {
        ADD, REMOVE;
    }
}

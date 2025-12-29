package oxy.bascenario.api.event.element.values;

import lombok.Builder;
import oxy.bascenario.api.effects.Easing;
import oxy.bascenario.api.event.api.Event;
import oxy.bascenario.api.utils.math.Vec2;

@Builder(toBuilder = true, builderClassName = "Builder")
public record PositionElementEvent(int id, Integer subId, long duration, Vec2 vec, Easing easing, Type type) implements Event {
    public PositionElementEvent(int id, long duration, Vec2 vec, Easing easing, Type type) {
        this(id, null, duration, vec, easing, type);
    }

    public enum Type {
        POSITION, OFFSET, SCALE, PIVOT
    }
}

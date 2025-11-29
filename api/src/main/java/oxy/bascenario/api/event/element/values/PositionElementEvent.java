package oxy.bascenario.api.event.element.values;

import lombok.Getter;
import oxy.bascenario.api.effects.Easing;
import oxy.bascenario.api.event.api.Event;
import oxy.bascenario.api.utils.math.Vec2;

@Getter
public class PositionElementEvent extends Event<PositionElementEvent> {
    private final int id;
    private final Integer subId;
    private final long duration;
    private final Vec2 vec;
    private final Easing easing;
    private final Type type;

    public PositionElementEvent(int id, Integer subId, long duration, Vec2 vec, Easing easing, Type type) {
        this.id = id;
        this.subId = subId;
        this.duration = duration;
        this.vec = vec;
        this.easing = easing;
        this.type = type;
    }

    public PositionElementEvent(int id, long duration, Vec2 vec, Easing easing, Type type) {
        this.id = id;
        this.subId = null;
        this.duration = duration;
        this.vec = vec;
        this.easing = easing;
        this.type = type;
    }

    @Override
    public String type() {
        return "position-element";
    }

    @Override
    public PositionElementEvent empty() {
        return new PositionElementEvent(0, 0, new Vec2(0, 0), Easing.LINEAR, Type.POSITION);
    }

    public enum Type {
        POSITION, OFFSET, SCALE
    }
}

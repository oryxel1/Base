package oxy.bascenario.api.event.element.values;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import oxy.bascenario.api.effects.Easing;
import oxy.bascenario.api.event.api.Event;
import oxy.bascenario.api.utils.math.Vec2;

@RequiredArgsConstructor
@Getter
public class PositionElementEvent extends Event<PositionElementEvent> {
    private final int id;
    private final long duration;
    private final Vec2 vec;
    private final Easing easing;
    private final Type type;

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

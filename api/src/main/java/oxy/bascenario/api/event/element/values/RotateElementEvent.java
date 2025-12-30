package oxy.bascenario.api.event.element.values;

import lombok.Builder;
import oxy.bascenario.api.effects.Easing;
import oxy.bascenario.api.event.api.Event;
import oxy.bascenario.api.utils.math.Vec3;

@Builder(toBuilder = true, builderClassName = "Builder")
public record RotateElementEvent(int id, Integer subId, int duration, Vec3 rotation, Easing easing) implements Event {
    public RotateElementEvent(int id, int duration, Vec3 rotation, Easing easing) {
        this(id, null, duration, rotation, easing);
    }
}

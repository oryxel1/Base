package oxy.base.api.event.element.values;

import lombok.Builder;
import oxy.base.api.effects.Easing;
import oxy.base.api.event.api.Event;
import oxy.base.api.utils.math.Vec3;

@Builder(toBuilder = true, builderClassName = "Builder")
public record RotateElementEvent(int id, Integer subId, int duration, Vec3 rotation, Easing easing) implements Event {
    public RotateElementEvent(int id, int duration, Vec3 rotation, Easing easing) {
        this(id, null, duration, rotation, easing);
    }
}

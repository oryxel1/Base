package oxy.bascenario.api.event.element.values;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import oxy.bascenario.api.effects.Easing;
import oxy.bascenario.api.event.api.Event;
import oxy.bascenario.api.utils.math.Vec3;

@RequiredArgsConstructor
@Builder(toBuilder = true, builderClassName = "Builder")
@EqualsAndHashCode(callSuper = false)
@Getter
public class RotateElementEvent extends Event<RotateElementEvent> {
    private final int id;
    private final Integer subId;
    private final long duration;
    private final Vec3 rotation;
    private final Easing easing;

    public RotateElementEvent(int id, long duration, Vec3 rotation, Easing easing) {
        this.id = id;
        this.subId = null;
        this.duration = duration;
        this.rotation = rotation;
        this.easing = easing;
    }

    @Override
    public String type() {
        return "rotate-element";
    }

    @Override
    public RotateElementEvent empty() {
        return new RotateElementEvent(0, 0, new Vec3(0, 0, 0), Easing.LINEAR);
    }
}

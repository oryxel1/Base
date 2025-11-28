package oxy.bascenario.api.event.element.values;

import lombok.Getter;
import oxy.bascenario.api.effects.Easing;
import oxy.bascenario.api.event.api.Event;
import oxy.bascenario.api.utils.math.Vec3;

@Getter
public class RotateElementEvent extends Event<RotateElementEvent> {
    private final int id;
    private final long duration;
    private final Vec3 rotation;
    private final Easing easing;

    public RotateElementEvent(int id, long duration, Vec3 rotation, Easing easing) {
        super(0);
        this.id = id;
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

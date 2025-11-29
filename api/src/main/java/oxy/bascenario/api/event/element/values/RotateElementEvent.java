package oxy.bascenario.api.event.element.values;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import oxy.bascenario.api.effects.Easing;
import oxy.bascenario.api.event.api.Event;
import oxy.bascenario.api.utils.math.Vec3;

@RequiredArgsConstructor
@Getter
public class RotateElementEvent extends Event<RotateElementEvent> {
    private final int id;
    private final long duration;
    private final Vec3 rotation;
    private final Easing easing;

    @Override
    public String type() {
        return "rotate-element";
    }

    @Override
    public RotateElementEvent empty() {
        return new RotateElementEvent(0, 0, new Vec3(0, 0, 0), Easing.LINEAR);
    }
}

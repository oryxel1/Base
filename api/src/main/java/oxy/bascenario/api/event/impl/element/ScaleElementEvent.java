package oxy.bascenario.api.event.impl.element;

import lombok.Getter;
import oxy.bascenario.api.effects.Easing;
import oxy.bascenario.api.event.Event;

@Getter
public class ScaleElementEvent extends Event<ScaleElementEvent> {
    private final int id;
    private final long duration;
    private final float scale;
    private final Easing easing;
    public ScaleElementEvent(int id, long duration, float scale, Easing easing) {
        super(0);
        this.id = id;
        this.duration = duration;
        this.scale = scale;
        this.easing = easing;
    }

    @Override
    public String type() {
        return "scale-element";
    }

    @Override
    public ScaleElementEvent empty() {
        return new ScaleElementEvent(0, 0, 1, Easing.QUAD);
    }
}

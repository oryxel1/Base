package oxy.bascenario.api.event.impl.element;

import lombok.Getter;
import oxy.bascenario.api.event.Event;

@Getter
public class MoveElementEvent extends Event<MoveElementEvent> {
    private final long duration;
    private final int id;
    private final float x, y;
    private final Type type;

    public MoveElementEvent(long duration, int id, float x, float y, Type type) {
        super(0);
        this.duration = duration;
        this.id = id;
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public MoveElementEvent(long duration, int id, float value, Type type) {
        super(0);
        this.duration = duration;
        this.id = id;
        this.x = type != Type.Y_ONLY ? value : 0;
        this.y = type != Type.X_ONLY ? value : 0;
        this.type = type;
    }

    @Override
    public String type() {
        return "move-element";
    }

    @Override
    public MoveElementEvent empty() {
        return new MoveElementEvent(0, 0, 50, 50, Type.BOTH);
    }

    public enum Type {
        X_ONLY, Y_ONLY, BOTH
    }
}

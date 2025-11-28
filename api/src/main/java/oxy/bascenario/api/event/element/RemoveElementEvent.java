package oxy.bascenario.api.event.element;

import lombok.Getter;
import oxy.bascenario.api.event.api.Event;

@Getter
public class RemoveElementEvent extends Event<RemoveElementEvent> {
    private final int id;

    public RemoveElementEvent(int id) {
        super(0);
        this.id = id;
    }

    @Override
    public String type() {
        return "remove-element";
    }

    @Override
    public RemoveElementEvent empty() {
        return new RemoveElementEvent(0);
    }
}

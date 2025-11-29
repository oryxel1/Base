package oxy.bascenario.api.event.element;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import oxy.bascenario.api.event.api.Event;

@Getter
public class RemoveElementEvent extends Event<RemoveElementEvent> {
    private final int id;
    private final Integer subId;

    public RemoveElementEvent(int id, Integer subId) {
        this.id = id;
        this.subId = subId;
    }

    public RemoveElementEvent(int id) {
        this.id = id;
        this.subId = null;
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

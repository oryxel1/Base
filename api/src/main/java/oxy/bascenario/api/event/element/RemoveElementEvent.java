package oxy.bascenario.api.event.element;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import oxy.bascenario.api.event.api.Event;

@RequiredArgsConstructor
@Getter
public class RemoveElementEvent extends Event<RemoveElementEvent> {
    private final int id;

    @Override
    public String type() {
        return "remove-element";
    }

    @Override
    public RemoveElementEvent empty() {
        return new RemoveElementEvent(0);
    }
}

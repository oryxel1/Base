package oxy.bascenario.api.event.element;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import oxy.bascenario.api.event.api.Event;

@RequiredArgsConstructor
@Getter
public class AttachElementEvent extends Event<AttachElementEvent> {
    private final int id;
    private final int subId;
    private final Object element;

    @Override
    public String type() {
        return "attach-element";
    }

    @Override
    public AttachElementEvent empty() {
        return new AttachElementEvent(0, 0, null);
    }
}

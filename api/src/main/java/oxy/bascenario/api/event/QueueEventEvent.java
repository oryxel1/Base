package oxy.bascenario.api.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import oxy.bascenario.api.event.api.Event;

@RequiredArgsConstructor
@Getter
public class QueueEventEvent extends Event<QueueEventEvent> {
    private final long duration;
    private final Event<?> event;

    @Override
    public String type() {
        return "queue-event";
    }

    @Override
    public QueueEventEvent empty() {
        return new QueueEventEvent(0, null);
    }
}

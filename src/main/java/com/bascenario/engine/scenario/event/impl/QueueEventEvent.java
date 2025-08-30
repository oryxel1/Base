package com.bascenario.engine.scenario.event.impl;

import com.bascenario.engine.scenario.event.api.Event;
import lombok.Getter;

public class QueueEventEvent extends Event {
    @Getter
    private final Event queuedEvent;
    public QueueEventEvent(long duration, Event event) {
        super(duration);
        this.queuedEvent = event;
    }
}

package com.bascenario.engine.scenario.event.impl;

import com.bascenario.engine.scenario.event.api.Event;
import com.bascenario.engine.scenario.event.render.EventRenderer;
import com.bascenario.engine.scenario.screen.ScenarioScreen;

public class QueueEventEvent extends Event {
    private final Event event;
    public QueueEventEvent(long duration, Event event) {
        super(duration);
        this.event = event;
    }

    @Override
    public void onEnd(ScenarioScreen screen) {
        screen.getEvents().add(new EventRenderer(this.event));
    }
}

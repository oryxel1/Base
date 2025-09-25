package com.bascenario.engine.scenario;

import com.bascenario.engine.scenario.event.api.Event;
import com.google.gson.annotations.SerializedName;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;
import java.util.Objects;

@EqualsAndHashCode
@ToString
public final class Timestamp {
    @SerializedName("wait-for-dialogue")
    private boolean waitForDialogue;
    private long time;
    private final List<Event<?>> events;

    public Timestamp(boolean waitForDialogue, long time, List<Event<?>> events) {
        this.waitForDialogue = waitForDialogue;
        this.time = time;
        this.events = events;
    }

    public boolean waitForDialogue() {
        return waitForDialogue;
    }

    public void waitForDialogue(boolean waitForDialogue) {
        this.waitForDialogue = waitForDialogue;
    }

    public long time() {
        return time;
    }

    public void time(long time) {
        this.time = time;
    }

    public List<Event<?>> events() {
        return events;
    }
}
package oxy.bascenario.engine.scenario;

import com.google.gson.annotations.SerializedName;
import oxy.bascenario.engine.scenario.event.base.Event;

import java.util.List;

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
        return this.waitForDialogue;
    }

    public void waitForDialogue(boolean waitForDialogue) {
        this.waitForDialogue = waitForDialogue;
    }

    public long time() {
        return this.time;
    }

    public void time(long time) {
        this.time = time;
    }

    public List<Event<?>> events() {
        return events;
    }
}
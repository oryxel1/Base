package oxy.bascenario.api;

import lombok.ToString;
import oxy.bascenario.api.event.api.Event;

import java.util.List;
import java.util.Objects;

@ToString
public class Timestamp {
    private boolean waitForDialogue;
    private int time;
    private final List<Event> events;

    public Timestamp(boolean waitForDialogue, int time, List<Event> events) {
        this.waitForDialogue = waitForDialogue;
        this.time = time;
        this.events = events;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Timestamp timestamp = (Timestamp) o;
        if (waitForDialogue != timestamp.waitForDialogue && time != timestamp.time || events.size() != timestamp.events.size()) {
            return false;
        }

        int i = 0;
        for (final Event event : events()) {
            if (!timestamp.events().get(i).equals(event)) {
                return false;
            }
            i++;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(waitForDialogue, time, events);
    }

    public boolean waitForDialogue() {
        return this.waitForDialogue;
    }

    public void waitForDialogue(boolean waitForDialogue) {
        this.waitForDialogue = waitForDialogue;
    }

    public int time() {
        return this.time;
    }

    public void time(int time) {
        this.time = time;
    }

    public List<Event> events() {
        return events;
    }
}

package oxy.base.api;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import oxy.base.api.event.api.Event;

import java.util.List;
import java.util.Objects;

@Accessors(fluent = true)
@Getter
@Setter
@ToString
public class Timestamp {
    private boolean waitForDialogue;
    private Integer dialogueIndex;
    private int time;
    private final List<Event> events;

    public Timestamp(boolean waitForDialogue, Integer dialogueIndex, int time, List<Event> events) {
        this.waitForDialogue = waitForDialogue;
        this.time = time;
        this.events = events;
        this.dialogueIndex = dialogueIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Timestamp timestamp = (Timestamp) o;
        if (waitForDialogue != timestamp.waitForDialogue || Objects.equals(dialogueIndex, timestamp.dialogueIndex) || time != timestamp.time || events.size() != timestamp.events.size()) {
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
}

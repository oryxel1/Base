package oxy.base.api.event.sound;

import oxy.base.api.event.api.Event;

public record SoundEvent(int id, Event event) implements Event {
    public enum Event {
        PAUSE, RESUME
    }
}

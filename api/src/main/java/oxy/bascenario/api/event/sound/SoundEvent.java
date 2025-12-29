package oxy.bascenario.api.event.sound;

import oxy.bascenario.api.event.api.Event;

public record SoundEvent(int id, Event event) implements Event {
    public enum Event {
        PAUSE, RESUME
    }
}

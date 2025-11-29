package oxy.bascenario.api.event.sound;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import oxy.bascenario.api.event.api.Event;

@RequiredArgsConstructor
@Getter
public class SoundEvent extends Event<SoundEvent> {
    private final int id;
    private final Event event;

    @Override
    public String type() {
        return "sound-event";
    }

    @Override
    public SoundEvent empty() {
        return new SoundEvent(0, Event.STOP);
    }

    public enum Event {
        PAUSE, STOP, RESUME
    }
}

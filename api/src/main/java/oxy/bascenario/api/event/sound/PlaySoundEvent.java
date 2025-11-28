package oxy.bascenario.api.event.sound;

import lombok.Getter;
import oxy.bascenario.api.effects.Sound;
import oxy.bascenario.api.event.api.Event;

@Getter
public class PlaySoundEvent extends Event<PlaySoundEvent> {
    private final Sound sound;
    public PlaySoundEvent(Sound sound) {
        super(0);
        this.sound = sound;
    }

    @Override
    public String type() {
        return "play-sound";
    }

    @Override
    public PlaySoundEvent empty() {
        return new PlaySoundEvent(null);
    }
}

package oxy.bascenario.api.event.sound;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import oxy.bascenario.api.effects.Sound;
import oxy.bascenario.api.event.api.Event;

@RequiredArgsConstructor
@Getter
public class PlaySoundEvent extends Event<PlaySoundEvent> {
    private final Sound sound;
    private final long duration;

    @Override
    public String type() {
        return "play-sound";
    }

    @Override
    public PlaySoundEvent empty() {
        return new PlaySoundEvent(null, 0);
    }
}

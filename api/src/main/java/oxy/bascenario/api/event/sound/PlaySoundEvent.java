package oxy.bascenario.api.event.sound;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import oxy.bascenario.api.effects.Sound;
import oxy.bascenario.api.event.api.Event;

@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder(toBuilder = true, builderClassName = "Builder")
@Getter
public class PlaySoundEvent extends Event<PlaySoundEvent> {
    private final Sound sound;
    private final long duration;
    private final float start;

    @Override
    public String type() {
        return "play-sound";
    }

    @Override
    public PlaySoundEvent empty() {
        return new PlaySoundEvent(null, 0, 0);
    }
}

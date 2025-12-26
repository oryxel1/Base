package oxy.bascenario.api.event.sound;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import oxy.bascenario.api.effects.Easing;
import oxy.bascenario.api.effects.Sound;
import oxy.bascenario.api.event.api.Event;

@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder(toBuilder = true, builderClassName = "Builder")
@Getter
public class SoundVolumeEvent extends Event<SoundVolumeEvent> {
    private final int id;
    private final long duration;
    private final float volume;
    private final Easing easing;

    @Override
    public String type() {
        return "sound-volume";
    }

    @Override
    public SoundVolumeEvent empty() {
        return new SoundVolumeEvent(0, 1000L, volume, Easing.LINEAR);
    }
}

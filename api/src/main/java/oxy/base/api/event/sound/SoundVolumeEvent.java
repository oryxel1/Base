package oxy.base.api.event.sound;

import lombok.Builder;
import oxy.base.api.effects.Easing;
import oxy.base.api.event.api.Event;

@Builder(toBuilder = true, builderClassName = "Builder")
public record SoundVolumeEvent(int id, int duration, float volume, Easing easing) implements Event {
}

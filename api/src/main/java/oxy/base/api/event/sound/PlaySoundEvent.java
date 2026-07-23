package oxy.base.api.event.sound;

import lombok.Builder;
import oxy.base.api.effects.Sound;
import oxy.base.api.event.api.Event;

@Builder(toBuilder = true, builderClassName = "Builder")
public record PlaySoundEvent(Sound sound, long duration, float start) implements Event {
}

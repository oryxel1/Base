package oxy.bascenario.api.event.sound;

import lombok.Builder;
import oxy.bascenario.api.effects.Sound;
import oxy.bascenario.api.event.api.Event;

@Builder(toBuilder = true, builderClassName = "Builder")
public record PlaySoundEvent(Sound sound, long duration, float start) implements Event {
}

package oxy.bascenario.api.event.sound;

import lombok.Builder;
import oxy.bascenario.api.effects.Easing;
import oxy.bascenario.api.event.api.Event;

@Builder(toBuilder = true, builderClassName = "Builder")
public record SoundVolumeEvent(int id, long duration, float volume, Easing easing) implements Event {
}

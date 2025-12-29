package oxy.bascenario.api.event.sound;

import lombok.Builder;
import oxy.bascenario.api.event.api.Event;

@Builder(toBuilder = true, builderClassName = "Builder")
public record StopSoundEvent(int id, int duration) implements Event {
}

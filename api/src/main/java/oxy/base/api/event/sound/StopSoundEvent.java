package oxy.base.api.event.sound;

import lombok.Builder;
import oxy.base.api.event.api.Event;

@Builder(toBuilder = true, builderClassName = "Builder")
public record StopSoundEvent(int id, int duration) implements Event {
}

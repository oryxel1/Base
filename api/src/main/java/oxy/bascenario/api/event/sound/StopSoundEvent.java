package oxy.bascenario.api.event.sound;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import oxy.bascenario.api.event.api.Event;

@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder(toBuilder = true)
@Getter
public class StopSoundEvent extends Event<StopSoundEvent> {
    private final int id;
    private final int duration;

    @Override
    public String type() {
        return "stop-sound";
    }

    @Override
    public StopSoundEvent empty() {
        return new StopSoundEvent(0, 1000);
    }
}

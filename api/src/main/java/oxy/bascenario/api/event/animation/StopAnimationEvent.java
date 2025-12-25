package oxy.bascenario.api.event.animation;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import oxy.bascenario.api.event.api.Event;

@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder(toBuilder = true)
@Getter
public class StopAnimationEvent extends Event<StopAnimationEvent> {
    private final int id;
    private final String name;

    @Override
    public String type() {
        return "stop-animation";
    }

    @Override
    public StopAnimationEvent empty() {
        return new StopAnimationEvent(id, "bascenarioengine:default-shake");
    }
}

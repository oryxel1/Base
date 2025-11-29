package oxy.bascenario.api.event.animation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import oxy.bascenario.api.event.api.Event;

@RequiredArgsConstructor
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

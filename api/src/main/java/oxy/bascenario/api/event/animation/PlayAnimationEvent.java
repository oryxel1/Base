package oxy.bascenario.api.event.animation;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import oxy.bascenario.api.event.api.Event;

@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder(toBuilder = true, builderClassName = "Builder")
@Getter
public class PlayAnimationEvent extends Event<PlayAnimationEvent> {
    private final int id;
    private final String name;
    private final boolean loop;

    @Override
    public String type() {
        return "play-animation";
    }

    @Override
    public PlayAnimationEvent empty() {
        return new PlayAnimationEvent(id, "bascenarioengine:default-shake", false);
    }
}

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
public class SpriteAnimationEvent extends Event<SpriteAnimationEvent> {
    private final int id;
    private final float mixTime;
    private final String animationName;
    private final int trackIndex;
    private final boolean loop;

    public SpriteAnimationEvent(int id, float mixTime, String animationName, int trackIndex) {
        this.id = id;
        this.mixTime = mixTime;
        this.animationName = animationName;
        this.trackIndex = trackIndex;
        this.loop = true;
    }

    @Override
    public String type() {
        return "sprite-animation";
    }

    @Override
    public SpriteAnimationEvent empty() {
        return new SpriteAnimationEvent(0, 0, "Idle_01", 0);
    }
}

package com.bascenario.engine.scenario.sprite;

import lombok.Getter;
import lombok.Setter;
import net.lenni0451.commons.animation.DynamicAnimation;
import net.lenni0451.commons.animation.easing.EasingFunction;
import net.lenni0451.commons.animation.easing.EasingMode;

/**
 *  The character sprite that get render/used, any is ok, this is not hardcoded.
 */
public class Sprite {
    // Where do the character sprite image stored?
    @Getter @Setter
    private String path;

    /**
     *  Start indicate the start time (in ms since start of scenario) where we should start rendering sprite,
     *  end (in ms since start of scenario) is when we stop rendering the sprite, if this sprite is never removed then any negative value will do.
     */
    private final long start, end;

    private DynamicAnimation xLocation, yLocation;

    public Sprite(String path, long start, long end, int x, int y) {
        this.path = path;
        this.start = start;
        this.end = end;
        this.xLocation = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_IN_OUT, 0, x);
        this.yLocation = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_IN_OUT, 0, y);
    }

    public void render(long duration) {
        if (this.end > 0 && duration > this.end || duration < this.start) {
            return;
        }

        // TODO: Render.
    }

    public void lerpTo(int targetX, int targetY, long duration) {
        if (duration < 1) {
            this.xLocation = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_IN_OUT, duration, targetX);
            this.yLocation = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_IN_OUT, duration, targetY);
            return;
        }

        this.xLocation = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_IN_OUT, duration, this.xLocation.getValue());
        this.yLocation = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_IN_OUT, duration, this.yLocation.getValue());
        this.xLocation.setTarget(targetX);
        this.yLocation.setTarget(targetY);
    }
}

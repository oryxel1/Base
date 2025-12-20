package oxy.bascenario.utils;

import lombok.Getter;
import net.lenni0451.commons.animation.easing.EasingFunction;
import net.lenni0451.commons.animation.easing.EasingMode;

/**
 * A dynamic animation with dynamically changing target values.<br>
 * The animation will automatically handle the easing and duration of the animation.
 */
public class DynamicAnimation {

    private final EasingFunction easingFunction;
    private final EasingMode easingMode;
    private final long durationPerUnit;
    private final float durationUnit;

    /**
     * -- GETTER --
     *
     * @return The start value of the animation
     */
    @Getter
    private float start;
    /**
     * -- GETTER --
     *
     * @return The target value of the animation
     */
    @Getter
    private float target;
    private long startTime;
    private long duration;

    public DynamicAnimation(final EasingFunction easingFunction, final EasingMode easingMode, final long duration, final float target) {
        this(easingFunction, easingMode, duration, 0, target);
    }

    public DynamicAnimation(final EasingFunction easingFunction, final EasingMode easingMode, final long durationPerUnit, final float durationUnit, final float target) {
        this.easingFunction = easingFunction;
        this.easingMode = easingMode;
        this.durationPerUnit = durationPerUnit;
        this.durationUnit = durationUnit;

        this.start = target;
        this.target = target;
    }

    /**
     * @return If the animation is currently running
     */
    public boolean isRunning() {
        return this.startTime != 0 && TimeUtils.currentTimeMillis() - this.startTime < this.duration;
    }

    /**
     * Stop the animation and keep the current value.<br>
     * It can be resumed by calling {@link #setTarget(float)}.
     *
     * @return The current instance
     */
    public DynamicAnimation stop() {
        this.target = this.getValue();
        this.startTime = 0;
        return this;
    }

    /**
     * Immediately stop the animation and jump to the target value.<br>
     * It can be resumed by calling {@link #setTarget(float)}.
     *
     * @return The current instance
     */
    public DynamicAnimation finish() {
        this.startTime = 0;
        return this;
    }

    /**
     * Set the target value of the animation.<br>
     * If the target value is the same as the current target value, nothing will happen.
     *
     * @param target The new target value
     * @return The current instance
     */
    public DynamicAnimation setTarget(final float target) {
        if (this.target == target) return this;
        this.start = this.getValue();
        this.target = target;
        this.startTime = TimeUtils.currentTimeMillis();
        if (this.durationUnit == 0) {
            this.duration = this.durationPerUnit;
        } else {
            this.duration = (long) (Math.abs(this.target - this.start) / this.durationUnit * this.durationPerUnit);
        }
        return this;
    }

    /**
     * Get the current value of the animation.
     *
     * @return The current value
     */
    public float getValue() {
        if (this.startTime == 0) return this.target;
        float progress = (float) (TimeUtils.currentTimeMillis() - this.startTime) / this.duration;
        if (progress > 1) return this.target;
        float position = this.easingMode.call(this.easingFunction, progress);
        return this.start + (this.target - this.start) * position;
    }

}

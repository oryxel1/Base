package oxy.bascenario.api.animation.frame;

import oxy.bascenario.api.animation.api.Animation;
import oxy.bascenario.api.animation.api.AnimationMode;
import oxy.bascenario.api.animation.api.AnimationOption;
import oxy.bascenario.api.effects.Easing;

import java.util.Comparator;
import java.util.List;

public class FrameAnimation<T> implements Animation {
    private final List<Frame<T>> frames;
    private final long duration;
    private final T start;

    private boolean reversed;

    @SuppressWarnings("ALL")
    @SafeVarargs
    public FrameAnimation(T start, Frame<T>... frames) {
        this.start = start;

        this.frames = List.of(frames);
        this.frames.sort(Comparator.comparingLong(f -> f.timestamp()));
        if (this.frames.isEmpty()) {
            this.duration = 0;
            return;
        }

        this.duration = this.frames.get(this.frames.size() - 1).timestamp();
    }

    private Frame<T> currentFrame(long timestamp) {
        Frame<T> frame = null;
        for (Frame<T> other : this.reversed ? this.frames.reversed() : this.frames) {
            if (this.reversed ? other.timestamp > timestamp : other.timestamp < timestamp) {
                continue;
            }

            frame = other;
            break;
        }

        return frame;
    }

    @Override
    public void render() {
    }

    @Override
    public long duration() {
        return this.duration;
    }

    @Override
    public String type() {
        return "frame";
    }

    public record Frame<T>(long timestamp, long duration, Easing easing, T value) {
    }

    private boolean resetOnPlay = true;
    private AnimationMode mode = AnimationMode.DEFAULT;
    @Override
    public List<AnimationOption<?>> options() {
        // Reset On Play
        return List.of(new AnimationOption<>("Reset on Play", true), new AnimationOption<>("Animation Mode", AnimationMode.DEFAULT));
    }

    @Override
    public void set(int index, Object value) {
        switch (index) {
            case 0 -> this.resetOnPlay = (boolean) value;
            case 1 -> this.mode = (AnimationMode) value;
        }
    }

    @SuppressWarnings("ALL")
    @Override
    public Object get(int index) {
        return switch (index) {
            case 0 -> this.resetOnPlay;
            case 1 -> this.mode;
            default -> null;
        };
    }
}

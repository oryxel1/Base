package oxy.bascenario.api.animation;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Animation {
//    private final String name;
//    private AnimationTimeline globalTimeline, defaultTimeline;
//
//    private float maxDuration;
//    private final Map<Float, AnimationTimeline> timelines = new HashMap<>();
//
//    private String initExpression;
//    private boolean resetWhenFinish;
//
//    public static Builder builder() {
//        return new Builder();
//    }
//
//    public static class Builder {
//        private String name;
//
//        private final AnimationTimeline.Builder global = new AnimationTimeline.Builder();
//        private final AnimationTimeline.Builder defaultTimeline = new AnimationTimeline.Builder();
//
//        private float maxDuration;
//        private final Map<Float, AnimationTimeline> timelines = new HashMap<>();
//
//        private String initExpression;
//        private boolean resetWhenFinish;
//
//        private Builder() {
//        }
//
//        public String name() {
//            return name;
//        }
//
//        public Builder name(String name) {
//            this.name = name;
//            return this;
//        }
//
//        public AnimationValue defaultRotation() {
//            return defaultTimeline.build().getRotation();
//        }
//
//        public Builder defaultRotation(AnimationValue rotation) {
//            defaultTimeline.rotation(rotation);
//            return this;
//        }
//
//        public AnimationValue defaultOffset() {
//            return defaultTimeline.build().getOffset();
//        }
//
//        public Builder defaultOffset(AnimationValue offset) {
//            defaultTimeline.offset(offset);
//            return this;
//        }
//
//        public AnimationValue defaultPivot() {
//            return defaultTimeline.build().getPivot();
//        }
//
//        public Builder defaultPivot(AnimationValue pivot) {
//            defaultTimeline.pivot(pivot);
//            return this;
//        }
//
//        public AnimationValue defaultScale() {
//            return defaultTimeline.build().getOffset();
//        }
//
//        public Builder defaultScale(AnimationValue scale) {
//            defaultTimeline.scale(scale);
//            return this;
//        }
//
//        public boolean resetWhenFinish() {
//            return resetWhenFinish;
//        }
//
//        public Builder resetWhenFinish(boolean resetWhenFinish) {
//            this.resetWhenFinish = resetWhenFinish;
//            return this;
//        }
//
//        public AnimationValue rotation() {
//            return global.build().getRotation();
//        }
//
//        public Builder rotation(AnimationValue rotation) {
//            global.rotation(rotation);
//            return this;
//        }
//
//        public AnimationValue offset() {
//            return global.build().getOffset();
//        }
//
//        public Builder offset(AnimationValue offset) {
//            global.offset(offset);
//            return this;
//        }
//
//        public AnimationValue pivot() {
//            return global.build().getPivot();
//        }
//
//        public Builder pivot(AnimationValue pivot) {
//            global.pivot(pivot);
//            return this;
//        }
//
//        public AnimationValue scale() {
//            return global.build().getOffset();
//        }
//
//        public Builder scale(AnimationValue scale) {
//            global.scale(scale);
//            return this;
//        }
//
//        public float maxDuration() {
//            return maxDuration;
//        }
//
//        public Builder maxDuration(float maxDuration) {
//            this.maxDuration = maxDuration;
//            return this;
//        }
//
//        public String initExpression() {
//            return initExpression;
//        }
//
//        public Builder initExpression(String initExpression) {
//            this.initExpression = initExpression;
//            return this;
//        }
//
//        public Map<Float, AnimationTimeline> timelines() {
//            return timelines;
//        }
//
//        public Builder put(float time, AnimationTimeline timeline) {
//            this.timelines.put(time, timeline);
//            return this;
//        }
//
//        public Animation build() {
//            final Animation animation = new Animation(this.name);
//            animation.globalTimeline = global.build();
//            animation.defaultTimeline = defaultTimeline.build();
//
//            animation.resetWhenFinish = resetWhenFinish;
//
//            animation.maxDuration = maxDuration;
//            animation.timelines.putAll(timelines);
//            animation.initExpression = initExpression;
//            return animation;
//        }
//    }
}

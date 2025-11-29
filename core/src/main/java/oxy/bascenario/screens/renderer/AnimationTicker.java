package oxy.bascenario.screens.renderer;

import oxy.bascenario.api.animation.Animation;
import oxy.bascenario.api.animation.AnimationTimeline;
import oxy.bascenario.api.animation.AnimationValue;
import oxy.bascenario.api.utils.math.Vec2;
import oxy.bascenario.api.utils.math.Vec3;
import oxy.bascenario.screens.ScenarioScreen;
import oxy.bascenario.screens.renderer.element.base.ElementRenderer;
import oxy.bascenario.utils.MochaUtils;
import oxy.bascenario.utils.animation.AnimationUtils;
import team.unnamed.mocha.runtime.Scope;
import team.unnamed.mocha.runtime.value.MutableObjectBinding;
import team.unnamed.mocha.runtime.value.Value;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public final class AnimationTicker {
    private final ElementRenderer<?> renderer;
    private final Scope baseScope;

    private long start = -1;
    private final long maxDuration;
    private final Map<Float, AnimationTimeline> timelines;

    private final Animation animation;

    private final ScenarioScreen screen;
    public AnimationTicker(ScenarioScreen screen, ElementRenderer<?> renderer, Animation animation) {
        this.renderer = renderer;
        this.baseScope = MochaUtils.BASE_SCOPE.copy();

        final MutableObjectBinding variableBinding = new MutableObjectBinding();
        this.baseScope.set("variable", variableBinding);
        this.baseScope.set("v", variableBinding);

        if (animation.getInitExpression() != null) {
            try {
                MochaUtils.eval(this.baseScope, animation.getInitExpression());
            } catch (IOException ignored) {
            }
        }
        this.baseScope.readOnly();

        this.timelines = new TreeMap<>(animation.getTimelines());
        this.maxDuration = animation.getMaxDuration() <= 0 ? -1 : (long) (animation.getMaxDuration() * 1000L);

        this.animation = animation;
        this.screen = screen;
    }

    private Scope scope;
    public void tick() {
        this.scope = this.baseScope.copy();

        final MutableObjectBinding query = new MutableObjectBinding();

        query.setFunction("present", (v) -> screen.getElements().get((int) v) == null ? 0 : 1);
        query.setFunction("offset", (i) -> i == 0 ? this.renderer.getOffset().x() : this.renderer.getOffset().y());
        query.setFunction("scale", (i) -> i == 0 ? this.renderer.getScale().x() : this.renderer.getScale().y());
        query.setFunction("rotation", (i) -> i == 0 ? this.renderer.getRotation().x() : this.renderer.getRotation().y());

        if (this.start == -1) {
            this.start = System.currentTimeMillis();
            query.set("startTime", Value.of(this.start));
        }

        final long animTime = System.currentTimeMillis() - this.start;
        query.set("anim_time", Value.of(animTime));
        query.set("alive_time", Value.of((System.currentTimeMillis() - this.renderer.getStart()) / 1000d));
        query.set("currentTimeMillis", Value.of(System.currentTimeMillis()));

        query.block();
        this.scope.set("query", query);
        this.scope.set("q", query);
        this.scope.readOnly();

        update(animation.getGlobalTimeline().getOffset(), Type.OFFSET, true);
        update(animation.getGlobalTimeline().getScale(), Type.SCALE, true);
        update(animation.getGlobalTimeline().getRotation(), Type.ROTATION, true);

        final Iterator<Map.Entry<Float, AnimationTimeline>> iterator = this.timelines.entrySet().iterator();
        while (iterator.hasNext()) {
            final Map.Entry<Float, AnimationTimeline> entry = iterator.next();
            final long timestamp = (long) (entry.getKey() * 1000L);
            if (animTime < timestamp) {
                break;
            }

            iterator.remove();

            final AnimationTimeline timeline = entry.getValue();
            try {
                if (timeline.getCondition() != null && !MochaUtils.eval(this.baseScope, timeline.getCondition()).getAsBoolean()) {
                    return;
                }
            } catch (Exception ignored) {}

            update(timeline.getOffset(), Type.OFFSET, false);
            update(timeline.getScale(), Type.SCALE, false);
            update(timeline.getRotation(), Type.ROTATION, false);
        }
    }

    public boolean safeToRemove() {
        boolean safe = this.timelines.isEmpty() && System.currentTimeMillis() - this.start >= this.maxDuration;
        if (safe) {
            resetWhenFinished();
        }
        return safe;
    }

    private void resetWhenFinished() {
        if (!this.animation.isResetWhenFinish()) {
            return;
        }

        update(this.animation.getDefaultTimeline().getOffset(), Type.OFFSET, false);
        update(this.animation.getDefaultTimeline().getScale(), Type.SCALE, false);
        update(this.animation.getDefaultTimeline().getRotation(), Type.ROTATION, false);
    }

    private void update(AnimationValue value, Type type, boolean global) {
        if (value == null || value.duration() == null || value.value() == null || value.value().length == 0 || value.easing() == null) {
            return;
        }

        try {
            long duration = (long) ((float)MochaUtils.eval(this.scope.copy(), value.duration()).getAsNumber() * 1000L);

            if (type == Type.ROTATION) {
                Vec3 vec3 = AnimationUtils.evalVec3(this.scope.copy(), value, true);
                if (vec3 != null) {
                    renderer.getRotation().set(AnimationUtils.toFunction(value.easing()), vec3, duration);
                }
            } else {
                Vec2 vec2 = AnimationUtils.evalVec2(this.scope.copy(), value, true);
                if (vec2 != null) {
                    ((type == Type.SCALE) ? renderer.getScale() : renderer.getOffset()).set(AnimationUtils.toFunction(value.easing()), vec2, duration, !global);
                }
            }
        } catch (Exception ignored) {
        }
    }

    private enum Type {
        ROTATION, SCALE, OFFSET
    }
}

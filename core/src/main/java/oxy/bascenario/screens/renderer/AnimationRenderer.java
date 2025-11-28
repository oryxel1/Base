package oxy.bascenario.screens.renderer;

import oxy.bascenario.api.animation.Animation;
import oxy.bascenario.screens.ScenarioScreen;
import oxy.bascenario.screens.renderer.element.base.ElementRenderer;
import oxy.bascenario.utils.MochaUtils;
import team.unnamed.mocha.runtime.Scope;
import team.unnamed.mocha.runtime.value.MutableObjectBinding;
import team.unnamed.mocha.runtime.value.Value;

import java.io.IOException;

public class AnimationRenderer {
    private final ElementRenderer<?> renderer;
    private final Animation animation;
    private final Scope scope;
    private final MutableObjectBinding query;

    private final long start = System.currentTimeMillis();

    public AnimationRenderer(ScenarioScreen screen, ElementRenderer<?> renderer, Animation animation) {
        this.renderer = renderer;
        this.animation = animation;
        this.scope = MochaUtils.BASE_SCOPE.copy();

        final MutableObjectBinding variableBinding = new MutableObjectBinding();
        this.scope.set("variable", variableBinding);
        this.scope.set("v", variableBinding);

        if (this.animation.getInitExpression() != null) {
            try {
                MochaUtils.eval(this.scope, this.animation.getInitExpression());
            } catch (IOException ignored) {
            }
        }

        this.query = new MutableObjectBinding();
        this.query.set("startTime", Value.of(this.start));
        this.query.setFunction("present", (v) -> screen.getElements().get((int) v) == null ? 0 : 1);
        this.scope.set("query", this.query);
        this.scope.set("q", this.query);
        this.scope.readOnly();

        try {
        } catch (Exception ignored) {
        }
    }

    public void tick() {
        this.query.set("anim_time", Value.of((System.currentTimeMillis() - this.start) / 1000d));
        this.query.set("alive_time", Value.of((System.currentTimeMillis() - this.renderer.getStart()) / 1000d));
        this.query.set("currentTimeMillis", Value.of(System.currentTimeMillis()));


    }

//    private Vec3 evalVec3(Scope scope, AnimationValue[] expression) {
//        return evalVec3(scope, expression, false);
//    }
//
//    private Vec2 evalVec2(Scope scope, AnimationValue[] expression) {
//        return evalVec2(scope, expression, false);
//    }

//    private Vec3 evalVec3(Scope scope, AnimationValue[] expression, boolean nullable) {
//        try {
//            return new Vec3(MochaUtils.eval(scope, expression[0].value()).getAsNumber(), MochaUtils.eval(scope, expression[1].value()).getAsNumber(), MochaUtils.eval(scope, expression[2].value()).getAsNumber());
//        } catch (IOException ignored) {
//            return nullable ? null : new Vec3(0, 0, 0);
//        }
//    }
//
//    private Vec2 evalVec2(Scope scope, AnimationValue[] expression, boolean nullable) {
//        try {
//            return new Vec2(MochaUtils.eval(scope, expression[0].value()).getAsNumber(), MochaUtils.eval(scope, expression[1].value()).getAsNumber());
//        } catch (IOException ignored) {
//            return nullable ? null : new Vec2(0, 0);
//        }
//    }
}

package oxy.base.screens.renderer.element.thingl.transition;

import net.lenni0451.commons.animation.easing.EasingFunction;
import net.raphimc.thingl.ThinGL;
import oxy.base.Base;
import oxy.base.api.effects.TransitionType;
import oxy.base.api.event.ScreenTransitionEvent;
import oxy.base.api.render.RenderLayer;
import oxy.base.screens.ScenarioScreen;
import oxy.base.screens.renderer.element.base.ElementRenderer;
import oxy.base.utils.TimeUtils;
import oxy.base.utils.animation.AnimationUtils;
import oxy.base.utils.animation.DynamicAnimation;

import static oxy.base.utils.thingl.ThinGLUtils.GLOBAL_RENDER_STACK;

public class HorizontalSwipeTransitionRenderer extends ElementRenderer<ScreenTransitionEvent> {
    private DynamicAnimation offset = AnimationUtils.build(element.outDuration(),
            element.type() == TransitionType.HORIZONTAL_SWIPE_LR ? -2500 : 2500,
            -372,
            EasingFunction.LINEAR);

    public HorizontalSwipeTransitionRenderer(ScreenTransitionEvent element, RenderLayer layer) {
        super(element, layer);
    }

    @Override
    protected void render(ScenarioScreen screen) {
        if (!offset.isRunning() && offset.getTarget() == -372) {
            long pass = offset.resolve(-372);
            long distance = TimeUtils.currentTimeMillis() - pass;
            if (pass > element.waitDuration()) {
                distance += element.waitDuration();

                if (element.background() != null) {
                    screen.background(element.background(), 0);
                }

                offset = AnimationUtils.build(element.inDuration(), distance, offset.getValue(),
                        element.type() == TransitionType.HORIZONTAL_SWIPE_LR ? 2500 : -2500
                        , EasingFunction.LINEAR);
            }
        }

        ThinGL.renderer2D().texture(GLOBAL_RENDER_STACK, Base.instance().assetsManager().texture("assets/base/uis/transition/swipe_h.png"),
                offset.getValue(), 0, 2800, 1080
        );
    }

    @Override
    public boolean selfDestruct() {
        return offset.getTarget() != -372 && !offset.isRunning();
    }
}

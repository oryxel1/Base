package oxy.base.screens.renderer.element.thingl.transition;

import net.lenni0451.commons.animation.easing.EasingFunction;
import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;
import oxy.base.api.effects.TransitionType;
import oxy.base.api.event.ScreenTransitionEvent;
import oxy.base.api.render.RenderLayer;
import oxy.base.screens.ScenarioScreen;
import oxy.base.screens.renderer.element.base.ElementRenderer;
import oxy.base.utils.TimeUtils;
import oxy.base.utils.animation.AnimationUtils;
import oxy.base.utils.animation.DynamicAnimation;

import static oxy.base.utils.thingl.ThinGLUtils.GLOBAL_RENDER_STACK;

public class FadeTransitionRenderer extends ElementRenderer<ScreenTransitionEvent> {
    private DynamicAnimation opacity = AnimationUtils.build(element.outDuration(), 0, 1, EasingFunction.LINEAR);

    public FadeTransitionRenderer(ScreenTransitionEvent element, RenderLayer layer) {
        super(element, layer);
    }

    @Override
    protected void render(ScenarioScreen screen) {
        if (!opacity.isRunning() && opacity.getTarget() == 1) {
            long pass = opacity.resolve(1);
            long distance = TimeUtils.currentTimeMillis() - pass;
            if (pass > element.waitDuration()) {
                distance += element.waitDuration();

                if (element.background() != null) {
                    screen.background(element.background(), 0);
                }

                opacity = AnimationUtils.build(element.inDuration(), distance, 1, 0, EasingFunction.LINEAR);
            }
        }

        ThinGL.renderer2D().filledRectangle(GLOBAL_RENDER_STACK, 0, 0, 1920, 1080,
                element.type() == TransitionType.FADE ? Color.BLACK.withAlphaF(opacity.getValue()) : Color.WHITE.withAlphaF(opacity.getValue()));
    }

    @Override
    public boolean selfDestruct() {
        return opacity.getTarget() == 0 && !opacity.isRunning();
    }
}

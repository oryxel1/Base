package oxy.bascenario.event.impl.transition;

import oxy.bascenario.api.event.ScreenTransitionEvent;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.event.base.FunctionEvent;
import oxy.bascenario.screens.ScenarioScreen;
import oxy.bascenario.screens.renderer.element.thingl.transition.FadeTransitionRenderer;
import oxy.bascenario.screens.renderer.element.thingl.transition.HorizontalSwipeTransitionRenderer;
import oxy.bascenario.screens.renderer.element.thingl.transition.VerticalSwipeTransitionRenderer;

public class FunctionScreenTransition extends FunctionEvent<ScreenTransitionEvent> {
    public FunctionScreenTransition(ScreenTransitionEvent event) {
        super(event);
    }

    @Override
    public void run(ScenarioScreen screen) {
        int id = Integer.MIN_VALUE + RenderLayer.values().length + 1;
        switch (event.type()) {
            case BACKGROUND_OVERLAP -> screen.background(event.background(), event.outDuration());
            case FADE, FADE_WHITE -> screen.getElements().put(id, new FadeTransitionRenderer(event, RenderLayer.TOP));
            case HORIZONTAL_SWIPE_RL, HORIZONTAL_SWIPE_LR -> screen.getElements().put(id, new HorizontalSwipeTransitionRenderer(event, RenderLayer.TOP));
            case VERTICAL_SWIPE_BT, VERTICAL_SWIPE_TB -> screen.getElements().put(id, new VerticalSwipeTransitionRenderer(event, RenderLayer.TOP));
        }
    }
}

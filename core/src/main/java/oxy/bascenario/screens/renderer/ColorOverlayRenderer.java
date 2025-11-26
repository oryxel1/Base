package oxy.bascenario.screens.renderer;

import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;
import oxy.bascenario.api.event.impl.ColorOverlayEvent;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.screens.renderer.base.ElementRenderer;
import oxy.bascenario.utils.ColorAnimations;
import oxy.bascenario.utils.ThinGLUtils;

public class ColorOverlayRenderer extends ElementRenderer<ColorOverlayEvent> {
    public ColorOverlayRenderer(RenderLayer layer) {
        super(null, layer);
        color = new ColorAnimations(Color.WHITE.withAlpha(0));
    }

    @Override
    protected void render() {
        ThinGL.renderer2D().filledRectangle(ThinGLUtils.GLOBAL_RENDER_STACK, 0, 0, 1920, 1080, color.color());
    }
}

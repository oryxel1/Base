package oxy.bascenario.screens.renderer.element.thingl.shape;

import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;
import oxy.bascenario.api.render.elements.shape.Rectangle;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.screens.renderer.element.base.ThinGLElementRenderer;

import static oxy.bascenario.utils.thingl.ThinGLUtils.GLOBAL_RENDER_STACK;

public class RectangleRenderer extends ThinGLElementRenderer<Rectangle> {
    public RectangleRenderer(Rectangle element, RenderLayer layer) {
        super(element, layer);
        color.set(element.color(), 0);
    }

    @Override
    protected void renderThinGL() {
        if (element.outlineOnly()) {
            ThinGL.renderer2D().outlinedRectangle(GLOBAL_RENDER_STACK, 0, 0, element.width(), element.height(), color.color(), 1);
        } else {
            ThinGL.renderer2D().filledRectangle(GLOBAL_RENDER_STACK, 0, 0, element.width(), element.height(), color.color());
        }

        if (this.overlayColor.color().getAlpha() != 0) {
            if (element.outlineOnly()) {
                ThinGL.renderer2D().outlinedRectangle(GLOBAL_RENDER_STACK, 0, 0, element.width(), element.height(), this.overlayColor.color(), 1);
            } else {
                ThinGL.renderer2D().filledRectangle(GLOBAL_RENDER_STACK, 0, 0, element.width(), element.height(), this.overlayColor.color());
            }
        }
    }
}

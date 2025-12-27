package oxy.bascenario.screens.renderer.element.thingl.shape;

import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;
import oxy.bascenario.api.render.elements.shape.Circle;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.screens.renderer.element.base.ThinGLElementRenderer;

import static oxy.bascenario.utils.ThinGLUtils.GLOBAL_RENDER_STACK;

public class CircleRenderer extends ThinGLElementRenderer<Circle> {
    public CircleRenderer(Circle element, RenderLayer layer) {
        super(element, layer);
        color.set(element.color(), 0);
    }

    @Override
    protected void renderThinGL() {
        if (element.outlineOnly()) {
            ThinGL.renderer2D().outlinedCircle(GLOBAL_RENDER_STACK, 0, 0, element.radius(), color.color(), 1);
        } else {
            ThinGL.renderer2D().filledCircle(GLOBAL_RENDER_STACK, 0, 0, element.radius(), color.color());
        }

        if (this.overlayColor.color().toRGBA() != Color.TRANSPARENT.toRGBA()) {
            if (element.outlineOnly()) {
                ThinGL.renderer2D().outlinedCircle(GLOBAL_RENDER_STACK, 0, 0, element.radius(), this.overlayColor.color(), 1);
            } else {
                ThinGL.renderer2D().filledCircle(GLOBAL_RENDER_STACK, 0, 0, element.radius(), this.overlayColor.color());
            }
        }
    }
}

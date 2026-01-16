package oxy.bascenario.screens.renderer.element.thingl.shape;

import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;
import oxy.bascenario.api.render.elements.shape.Triangle;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.screens.renderer.element.base.ThinGLElementRenderer;

import static oxy.bascenario.utils.thingl.ThinGLUtils.GLOBAL_RENDER_STACK;

public class TriangleRenderer extends ThinGLElementRenderer<Triangle> {
    public TriangleRenderer(Triangle element, RenderLayer layer) {
        super(element, layer);
        color.set(element.color(), 0);
    }

    @Override
    protected void renderThinGL() {
        ThinGL.renderer2D().filledTriangle(GLOBAL_RENDER_STACK, element.x1(), element.y1(), element.x2(), element.y2(), element.x3(), element.y3(), color.color());

        if (this.overlayColor.color().toRGBA() != Color.TRANSPARENT.toRGBA()) {
            ThinGL.renderer2D().filledTriangle(GLOBAL_RENDER_STACK, element.x1(), element.y1(), element.x2(), element.y2(), element.x3(), element.y3(), this.overlayColor.color());
        }
    }
}

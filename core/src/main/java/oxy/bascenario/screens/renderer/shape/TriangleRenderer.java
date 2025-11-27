package oxy.bascenario.screens.renderer.shape;

import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;
import oxy.bascenario.api.elements.shape.Triangle;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.screens.renderer.base.ElementRenderer;

import static oxy.bascenario.utils.ThinGLUtils.GLOBAL_RENDER_STACK;

public class TriangleRenderer extends ElementRenderer<Triangle> {
    public TriangleRenderer(Triangle element, RenderLayer layer) {
        super(element, layer);
    }

    @Override
    protected void render() {
        GLOBAL_RENDER_STACK.pushMatrix();
        GLOBAL_RENDER_STACK.translate(this.x.getValue(), this.y.getValue(), 0);
        GLOBAL_RENDER_STACK.scale(this.scale.getValue());

        ThinGL.renderer2D().filledTriangle(GLOBAL_RENDER_STACK, element.x1(), element.y1(), element.x2(), element.y2(), element.x3(), element.y3(), element.color());

        if (this.color.color().toRGBA() != Color.WHITE.toRGBA()) {
            ThinGL.renderer2D().filledTriangle(GLOBAL_RENDER_STACK, element.x1(), element.y1(), element.x2(), element.y2(), element.x3(), element.y3(), this.color.color());
        }

        GLOBAL_RENDER_STACK.popMatrix();
    }
}

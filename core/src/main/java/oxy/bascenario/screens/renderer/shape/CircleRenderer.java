package oxy.bascenario.screens.renderer.shape;

import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;
import oxy.bascenario.api.elements.shape.Circle;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.screens.renderer.base.ElementRenderer;

import static oxy.bascenario.utils.ThinGLUtils.GLOBAL_RENDER_STACK;

public class CircleRenderer extends ElementRenderer<Circle> {
    public CircleRenderer(Circle element, RenderLayer layer) {
        super(element, layer);
    }

    @Override
    protected void render() {
        GLOBAL_RENDER_STACK.pushMatrix();
        GLOBAL_RENDER_STACK.scale(this.scale.getValue());

        if (element.outlineOnly()) {
            ThinGL.renderer2D().outlinedCircle(GLOBAL_RENDER_STACK, this.x.getValue(), this.y.getValue(), element.radius(), element.color(), 1);
        } else {
            ThinGL.renderer2D().filledCircle(GLOBAL_RENDER_STACK, this.x.getValue(), this.y.getValue(), element.radius(), element.color());
        }

        if (this.color.color().toRGBA() != Color.WHITE.toRGBA()) {
            if (element.outlineOnly()) {
                ThinGL.renderer2D().outlinedCircle(GLOBAL_RENDER_STACK, this.x.getValue(), this.y.getValue(), element.radius(), this.color.color(), 1);
            } else {
                ThinGL.renderer2D().filledCircle(GLOBAL_RENDER_STACK, this.x.getValue(), this.y.getValue(), element.radius(), this.color.color());
            }
        }

        GLOBAL_RENDER_STACK.popMatrix();
    }
}

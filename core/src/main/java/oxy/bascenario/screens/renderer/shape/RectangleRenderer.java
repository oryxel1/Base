package oxy.bascenario.screens.renderer.shape;

import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;
import oxy.bascenario.api.elements.shape.Rectangle;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.screens.renderer.base.ElementRenderer;

import static oxy.bascenario.utils.ThinGLUtils.GLOBAL_RENDER_STACK;

public class RectangleRenderer extends ElementRenderer<Rectangle> {
    public RectangleRenderer(Rectangle element, RenderLayer layer) {
        super(element, layer);
    }

    @Override
    protected void render() {
        GLOBAL_RENDER_STACK.pushMatrix();
        GLOBAL_RENDER_STACK.scale(this.scale.getValue());

        float x = this.x.getValue(), y = this.y.getValue();
        if (element.outlineOnly()) {
            ThinGL.renderer2D().outlinedRectangle(GLOBAL_RENDER_STACK, x, y, x + element.width(), y + element.height(), element.color(), 1);
        } else {
            ThinGL.renderer2D().filledRectangle(GLOBAL_RENDER_STACK, x, y, x + element.width(), y + element.height(), element.color());
        }

        if (this.color.color().toRGBA() != Color.WHITE.toRGBA()) {
            if (element.outlineOnly()) {
                ThinGL.renderer2D().outlinedRectangle(GLOBAL_RENDER_STACK, x, y, x + element.width(), y + element.height(), this.color.color(), 1);
            } else {
                ThinGL.renderer2D().filledRectangle(GLOBAL_RENDER_STACK, x, y, x + element.width(), y + element.height(), this.color.color());
            }
        }

        GLOBAL_RENDER_STACK.popMatrix();
    }
}

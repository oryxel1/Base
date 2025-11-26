package oxy.bascenario.screens.renderer;

import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;
import oxy.bascenario.api.elements.RendererImage;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.managers.TextureManager;
import oxy.bascenario.screens.renderer.base.ElementRenderer;

import static oxy.bascenario.utils.ThinGLUtils.GLOBAL_RENDER_STACK;

public class ImageRenderer extends ElementRenderer<RendererImage> {
    public ImageRenderer(RendererImage element, RenderLayer layer) {
        super(element, layer);
    }

    @Override
    protected void render() {
        GLOBAL_RENDER_STACK.pushMatrix();
        GLOBAL_RENDER_STACK.scale(this.scale.getValue());
        render(element.color());
        if (color.color().toRGBA() != Color.WHITE.toRGBA()) {
            render(color.color());
        }
        GLOBAL_RENDER_STACK.popMatrix();
    }

    private void render(Color color) {
        ThinGL.renderer2D().coloredTexture(GLOBAL_RENDER_STACK, TextureManager.getInstance().getTexture(element.image().file()), this.x.getValue(), this.y.getValue(), element.width(), element.height(), color);
    }
}

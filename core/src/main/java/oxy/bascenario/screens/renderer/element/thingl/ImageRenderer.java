package oxy.bascenario.screens.renderer.element.thingl;

import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;
import oxy.bascenario.api.render.elements.RendererImage;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.managers.TextureManager;
import oxy.bascenario.screens.renderer.element.base.ThinGLElementRenderer;

import static oxy.bascenario.utils.ThinGLUtils.GLOBAL_RENDER_STACK;

public class ImageRenderer extends ThinGLElementRenderer<RendererImage> {
    public ImageRenderer(RendererImage element, RenderLayer layer) {
        super(element, layer);
    }

    @Override
    protected void renderThinGL() {
        ThinGL.renderer2D().coloredTexture(GLOBAL_RENDER_STACK, TextureManager.getInstance().getTexture(element.image().file()), 0, 0, element.width(), element.height(), element.color());

        if (color.color().toRGBA() != Color.WHITE.toRGBA()) {
            ThinGL.renderer2D().coloredTexture(GLOBAL_RENDER_STACK, TextureManager.getInstance().getTexture(element.image().file()), 0, 0, element.width(), element.height(), color.color());
        }
    }
}

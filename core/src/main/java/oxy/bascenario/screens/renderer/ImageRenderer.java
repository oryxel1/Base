package oxy.bascenario.screens.renderer;

import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;
import oxy.bascenario.api.elements.RendererImage;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.managers.TextureManager;
import oxy.bascenario.screens.renderer.base.ElementRenderer;
import oxy.bascenario.utils.ThinGLUtils;

public class ImageRenderer extends ElementRenderer<RendererImage> {
    public ImageRenderer(RendererImage element, RenderLayer layer) {
        super(element, layer);
    }

    @Override
    public void render() {
        ThinGL.renderer2D().coloredTexture(
                ThinGLUtils.GLOBAL_RENDER_STACK,
                TextureManager.getInstance().getTexture(element.image().file()),
                this.x.getValue(),
                this.y.getValue(),
                element.width(),
                element.height(),
                element.color()
        );

        if (color.color().toRGBA() != Color.WHITE.toRGBA()) {
            ThinGL.renderer2D().coloredTexture(ThinGLUtils.GLOBAL_RENDER_STACK,
                    TextureManager.getInstance().getTexture(element.image().file()),
                    this.x.getValue(),
                    this.y.getValue(),
                    element.width(),
                    element.height(),
                    color.color()
            );
        }
    }
}

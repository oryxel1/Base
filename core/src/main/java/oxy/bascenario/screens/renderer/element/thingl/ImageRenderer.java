package oxy.bascenario.screens.renderer.element.thingl;

import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.gl.resource.image.texture.impl.Texture2D;
import oxy.bascenario.api.Scenario;
import oxy.bascenario.api.render.elements.RendererImage;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.api.utils.FileInfo;
import oxy.bascenario.managers.TextureManager;
import oxy.bascenario.screens.renderer.element.base.ThinGLElementRenderer;

import static oxy.bascenario.utils.ThinGLUtils.GLOBAL_RENDER_STACK;

public class ImageRenderer extends ThinGLElementRenderer<RendererImage<FileInfo>> {
    private final Texture2D texture2D;
    public ImageRenderer(RendererImage<FileInfo> element, RenderLayer layer, Scenario scenario) {
        super(element, layer);
        this.texture2D = TextureManager.getInstance().getTexture(scenario, element.image());
    }

    @Override
    protected void renderThinGL() {
        ThinGL.renderer2D().coloredTexture(GLOBAL_RENDER_STACK, texture2D, 0, 0, element.width(), element.height(), element.color());

        if (color.color().toRGBA() != Color.TRANSPARENT.toRGBA()) {
            ThinGL.renderer2D().coloredTexture(GLOBAL_RENDER_STACK, texture2D, 0, 0, element.width(), element.height(), color.color());
        }
    }
}

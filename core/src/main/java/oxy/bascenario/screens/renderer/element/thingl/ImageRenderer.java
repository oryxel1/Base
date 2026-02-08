package oxy.bascenario.screens.renderer.element.thingl;

import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.gl.resource.image.texture.impl.Texture2D;
import oxy.bascenario.Base;
import oxy.bascenario.api.Scenario;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.api.render.elements.image.Image;
import oxy.bascenario.screens.renderer.element.base.ThinGLElementRenderer;

import static oxy.bascenario.utils.thingl.ThinGLUtils.GLOBAL_RENDER_STACK;

public class ImageRenderer extends ThinGLElementRenderer<Image> {
    private final Texture2D texture2D;
    public ImageRenderer(Image element, RenderLayer layer, Scenario scenario) {
        super(element, layer);
        this.texture2D = Base.instance().assetsManager().texture(scenario.getName(), element.file());
        color.set(element.color(), 0);
    }

    @Override
    protected void renderThinGL() {
        ThinGL.renderer2D().coloredTexture(GLOBAL_RENDER_STACK, texture2D, 0, 0, element.width(), element.height(), color.color());

        if (overlayColor.color().getAlpha() != 0) {
            ThinGL.renderer2D().coloredTexture(GLOBAL_RENDER_STACK, texture2D, 0, 0, element.width(), element.height(), overlayColor.color());
        }
    }
}

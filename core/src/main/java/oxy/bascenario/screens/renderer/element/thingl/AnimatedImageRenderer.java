package oxy.bascenario.screens.renderer.element.thingl;

import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.gl.texture.animated.SequencedTexture;
import net.raphimc.thingl.image.animated.impl.AwtGifImage;
import oxy.bascenario.Base;
import oxy.bascenario.api.Scenario;
import oxy.bascenario.api.managers.other.Asset;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.api.render.elements.RendererImage;
import oxy.bascenario.api.render.elements.image.AnimatedImage;
import oxy.bascenario.api.utils.FileInfo;
import oxy.bascenario.screens.renderer.element.base.ThinGLElementRenderer;
import oxy.bascenario.utils.TimeUtils;

import java.io.File;
import java.nio.file.Files;
import java.util.Objects;

import static oxy.bascenario.utils.ThinGLUtils.GLOBAL_RENDER_STACK;

public class AnimatedImageRenderer extends ThinGLElementRenderer<RendererImage<AnimatedImage>> {
    private SequencedTexture texture;
    private long startTime;

    public AnimatedImageRenderer(Scenario scenario, RendererImage<AnimatedImage> element, RenderLayer layer) {
        super(element, layer);

        try {
            Asset<AwtGifImage> asset = Base.instance().assetsManager().assets(scenario.getName(), element.image().file());
            this.texture = new SequencedTexture(asset.asset());
        } catch (Exception ignored) {
            this.texture = null;
            return;
        }

        this.startTime = TimeUtils.currentTimeMillis() - element.image().start();
    }

    @Override
    protected void renderThinGL() {
        if (this.texture == null) {
            return;
        }

        int time = (int) (TimeUtils.currentTimeMillis() - this.startTime);
        if (!this.element.image().loop()) {
            time = Math.min(this.texture.getDuration(), time);
        }

        ThinGL.renderer2D().textureArrayLayer(GLOBAL_RENDER_STACK, texture, this.texture.getFrameIndex(time), 0, 0, element.width(), element.height()/*, element.color()*/);

        if (color.color().toRGBA() != Color.WHITE.toRGBA()) {
            ThinGL.renderer2D().textureArrayLayer(GLOBAL_RENDER_STACK, texture, this.texture.getFrameIndex(time), 0, 0, element.width(), element.height()/*, element.color()*/);
        }
    }
}

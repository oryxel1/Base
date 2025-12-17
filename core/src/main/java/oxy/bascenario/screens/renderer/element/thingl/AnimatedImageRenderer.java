package oxy.bascenario.screens.renderer.element.thingl;

import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.gl.texture.animated.SequencedTexture;
import net.raphimc.thingl.image.animated.impl.AwtGifImage;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.api.render.elements.RendererImage;
import oxy.bascenario.api.render.elements.image.AnimatedImage;
import oxy.bascenario.screens.renderer.element.base.ThinGLElementRenderer;

import java.io.File;
import java.nio.file.Files;
import java.util.Objects;

import static oxy.bascenario.utils.ThinGLUtils.GLOBAL_RENDER_STACK;

public class AnimatedImageRenderer extends ThinGLElementRenderer<RendererImage> {
    private SequencedTexture texture;
    private long startTime;

    public AnimatedImageRenderer(RendererImage element, RenderLayer layer) {
        super(element, layer);

        final byte[] imageBytes;
        try {
            // TODO: None direct support.
            imageBytes = element.image().file().internal() ? Objects.requireNonNull(AnimatedImageRenderer.class.getResourceAsStream("/" + element.image().file().path())).readAllBytes() : Files.readAllBytes(new File(element.image().file().path()).toPath());
            this.texture = new SequencedTexture(new AwtGifImage(imageBytes));
        } catch (Exception ignored) {
            this.texture = null;
            return;
        }

        this.startTime = System.currentTimeMillis();
    }

    @Override
    protected void renderThinGL() {
        if (this.texture == null) {
            return;
        }

        int time = (int) (System.currentTimeMillis() - this.startTime);
        if (!((AnimatedImage)this.element.image()).isLoop()) {
            time = Math.min(this.texture.getDuration(), time);
        }

        ThinGL.renderer2D().textureArrayLayer(GLOBAL_RENDER_STACK, texture, this.texture.getFrameIndex(time), 0, 0, element.width(), element.height()/*, element.color()*/);

        if (color.color().toRGBA() != Color.WHITE.toRGBA()) {
            ThinGL.renderer2D().textureArrayLayer(GLOBAL_RENDER_STACK, texture, this.texture.getFrameIndex(time), 0, 0, element.width(), element.height()/*, element.color()*/);
        }
    }
}

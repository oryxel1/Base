package oxy.bascenario.screens.renderer.element.thingl;

import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.gl.texture.animated.SequencedTexture;
import oxy.bascenario.Base;
import oxy.bascenario.api.Scenario;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.api.render.elements.image.AnimatedImage;
import oxy.bascenario.screens.renderer.element.base.ThinGLElementRenderer;
import oxy.bascenario.utils.TimeUtils;

import static oxy.bascenario.utils.ThinGLUtils.GLOBAL_RENDER_STACK;

public class AnimatedImageRenderer extends ThinGLElementRenderer<AnimatedImage> {
    private SequencedTexture texture;
    private long startTime;

    public AnimatedImageRenderer(Scenario scenario, AnimatedImage element, RenderLayer layer) {
        super(element, layer);

        try {
            this.texture = (SequencedTexture) Base.instance().assetsManager().assets(scenario.getName(), element.file()).asset();
        } catch (Exception ignored) {
            this.texture = null;
            return;
        }

        this.startTime = TimeUtils.currentTimeMillis() - element.start();
        color.set(element.color(), 0);
    }

    @Override
    protected void renderThinGL() {
        if (this.texture == null) {
            return;
        }

        int time = (int) (TimeUtils.currentTimeMillis() - this.startTime);
        if (!this.element.loop()) {
            time = Math.min(this.texture.getDuration(), time);
        }

        ThinGL.globalUniforms().setColorModifier(color.color());
        ThinGL.renderer2D().textureArrayLayer(GLOBAL_RENDER_STACK, texture, this.texture.getFrameIndex(time), 0, 0, element.width(), element.height()/*, element.color()*/);
        ThinGL.globalUniforms().setColorModifier(Color.WHITE);

        if (overlayColor.color().toRGBA() != Color.TRANSPARENT.toRGBA()) {
            ThinGL.globalUniforms().setColorModifier(overlayColor.color());
            ThinGL.renderer2D().textureArrayLayer(GLOBAL_RENDER_STACK, texture, this.texture.getFrameIndex(time), 0, 0, element.width(), element.height()/*, element.color()*/);
            ThinGL.globalUniforms().setColorModifier(Color.WHITE);
        }
    }
}

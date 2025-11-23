package oxy.bascenario.screens.renderer;

import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.renderer.impl.RendererText;
import net.raphimc.thingl.text.TextRun;
import net.raphimc.thingl.text.font.Font;
import oxy.bascenario.api.elements.FontType;
import oxy.bascenario.api.elements.Text;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.screens.renderer.base.ElementRenderer;
import oxy.bascenario.utils.ColorAnimations;
import oxy.bascenario.utils.FontUtils;
import oxy.bascenario.utils.ThinGLUtils;

import static oxy.bascenario.utils.ThinGLUtils.GLOBAL_RENDER_STACK;

public class TextRenderer extends ElementRenderer<Text> {
    private final Font font;
    public TextRenderer(Text element, RenderLayer layer) {
        super(element, layer);

        color = new ColorAnimations(element.color());
        font = FontUtils.getFont(FontType.toName(element.type()), element.size());
    }

    @Override
    public void render() {
        GLOBAL_RENDER_STACK.pushMatrix();
        GLOBAL_RENDER_STACK.scale(this.scale.getValue());
        ThinGL.rendererText().textRun(
                ThinGLUtils.GLOBAL_RENDER_STACK,
                TextRun.fromString(font, element.text(), this.color.color()),
                x.getValue(), y.getValue(), RendererText.VerticalOrigin.BASELINE, RendererText.HorizontalOrigin.VISUAL_LEFT
        );
        GLOBAL_RENDER_STACK.popMatrix();
    }
}

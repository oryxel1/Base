package oxy.bascenario.screens.renderer;

import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.renderer.impl.RendererText;
import net.raphimc.thingl.text.TextLine;
import net.raphimc.thingl.text.TextRun;
import net.raphimc.thingl.text.TextSegment;
import net.raphimc.thingl.text.font.Font;
import oxy.bascenario.api.elements.text.FontType;
import oxy.bascenario.api.elements.text.Text;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.screens.renderer.base.ElementRenderer;
import oxy.bascenario.utils.FontUtils;
import oxy.bascenario.utils.ThinGLUtils;

import java.util.ArrayList;

import static oxy.bascenario.utils.ThinGLUtils.GLOBAL_RENDER_STACK;

public class TextRenderer extends ElementRenderer<Text> {
    private final TextLine line;

    public TextRenderer(Text element, RenderLayer layer) {
        super(element, layer);

        this.line = new TextLine(new ArrayList<>());

        for (oxy.bascenario.api.elements.text.TextSegment segment : element.segments()) {
            Font font = FontUtils.toFont(segment, element);
            line.add(new TextRun(font, new TextSegment(segment.text(), segment.color(), segment.toFlags(), segment.outline().isPresent() ? segment.outline().get() : Color.TRANSPARENT)));
        }
    }

    @Override
    public void render() {
        GLOBAL_RENDER_STACK.pushMatrix();
        GLOBAL_RENDER_STACK.scale(this.scale.getValue());

        ThinGL.rendererText().textLine(ThinGLUtils.GLOBAL_RENDER_STACK, line, x.getValue(), y.getValue(), RendererText.VerticalOrigin.BASELINE, RendererText.HorizontalOrigin.VISUAL_LEFT);

        if (this.color.color().toRGBA() != Color.WHITE.toRGBA()) {
            ThinGL.programs().getColorTweak().bindInput();

            ThinGL.rendererText().textLine(ThinGLUtils.GLOBAL_RENDER_STACK, line, x.getValue(), y.getValue(), RendererText.VerticalOrigin.BASELINE, RendererText.HorizontalOrigin.VISUAL_LEFT);

            ThinGL.programs().getColorTweak().unbindInput();
            ThinGL.programs().getColorTweak().configureParameters(this.color.color());
            ThinGL.programs().getColorTweak().render(0, 0, 1920, 1080);
            ThinGL.programs().getColorTweak().clearInput();
        }

        GLOBAL_RENDER_STACK.popMatrix();
    }
}

package oxy.bascenario.screens.renderer.element.thingl;

import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.gl.renderer.impl.RendererText;
import net.raphimc.thingl.resource.font.Font;
import net.raphimc.thingl.text.TextLine;
import net.raphimc.thingl.text.TextRun;
import net.raphimc.thingl.text.TextSegment;
import oxy.bascenario.api.Scenario;
import oxy.bascenario.api.render.elements.text.Text;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.screens.renderer.element.base.ThinGLElementRenderer;
import oxy.bascenario.utils.FontUtils;
import oxy.bascenario.utils.ThinGLUtils;

import java.util.ArrayList;

public class TextRenderer extends ThinGLElementRenderer<Text> {
    private final TextLine line;

    public TextRenderer(Text element, RenderLayer layer, Scenario scenario) {
        super(element, layer);

        this.line = new TextLine(new ArrayList<>());

        for (oxy.bascenario.api.render.elements.text.TextSegment segment : element.segments()) {
            Font font = FontUtils.toFont(scenario, segment, element);
            line.add(new TextRun(font, new TextSegment(segment.text(), segment.color(), segment.toFlags(), segment.outline().isPresent() ? segment.outline().get() : Color.TRANSPARENT)));
        }
    }

    @Override
    protected void renderThinGL() {
        ThinGL.rendererText().textLine(ThinGLUtils.GLOBAL_RENDER_STACK, line, 0, 0, RendererText.VerticalOrigin.BASELINE, RendererText.HorizontalOrigin.VISUAL_LEFT);

        if (this.overlayColor.color().toRGBA() != Color.TRANSPARENT.toRGBA()) {
            ThinGL.programs().getColorTweak().bindInput();

            ThinGL.rendererText().textLine(ThinGLUtils.GLOBAL_RENDER_STACK, line, 0, 0, RendererText.VerticalOrigin.BASELINE, RendererText.HorizontalOrigin.VISUAL_LEFT);

            ThinGL.programs().getColorTweak().unbindInput();
            ThinGL.programs().getColorTweak().configureParameters(this.overlayColor.color());
            ThinGL.programs().getColorTweak().render(0, 0, 1920, 1080);
            ThinGL.programs().getColorTweak().clearInput();
        }
    }
}

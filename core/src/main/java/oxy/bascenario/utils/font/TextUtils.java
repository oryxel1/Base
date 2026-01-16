package oxy.bascenario.utils.font;

import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.gl.renderer.impl.RendererText;
import net.raphimc.thingl.text.TextLine;
import net.raphimc.thingl.text.TextRun;
import net.raphimc.thingl.text.shaping.ShapedTextLine;
import net.raphimc.thingl.text.shaping.ShapedTextRun;

import static oxy.bascenario.utils.thingl.ThinGLUtils.GLOBAL_RENDER_STACK;

public class TextUtils {
    public static float getVisualWidth(float size, final ShapedTextLine textLine) {
        return textLine.visualBounds().lengthX() * (size / 65f);
    }

    public static float getVisualWidth(float size, final ShapedTextRun textRun) {
        return textRun.visualBounds().lengthX() * (size / 65f);
    }

    public static float getVisualHeight(float size, final ShapedTextLine textLine) {
        return textLine.visualBounds().lengthY() * (size / 65f);
    }

    public static float getVisualHeight(float size, final ShapedTextRun textRun) {
        return textRun.visualBounds().lengthY() * (size / 65f);
    }

    public static float getLogicalHeight(float size, final ShapedTextLine textLine) {
        return textLine.logicalBounds().lengthY() * (size / 65f);
    }

    public static float getLogicalHeight(float size, final ShapedTextRun textRun) {
        return textRun.logicalBounds().lengthY() * (size / 65f);
    }

    public static void textRun(float size, final TextRun run, float x, float y) {
        ThinGL.rendererText().pushGlobalScale(size / 65f);
        ThinGL.rendererText().textRun(GLOBAL_RENDER_STACK, run, x, y);
        ThinGL.rendererText().popGlobalScale();
    }

    public static void textRun(float size, final TextRun run, float x, float y, RendererText.VerticalOrigin verticalOrigin, RendererText.HorizontalOrigin horizontalOrigin) {
        ThinGL.rendererText().pushGlobalScale(size / 65f);
        ThinGL.rendererText().textRun(GLOBAL_RENDER_STACK, run, x, y, verticalOrigin, horizontalOrigin);
        ThinGL.rendererText().popGlobalScale();
    }

    public static void textLine(float size, final TextLine textLine, final float x, final float y) {
        ThinGL.rendererText().pushGlobalScale(size / 65f);
        ThinGL.rendererText().textLine(GLOBAL_RENDER_STACK, textLine.shape(), x, y);
        ThinGL.rendererText().popGlobalScale();
    }

    public static void textLine(float size, final TextLine textLine, final float x, final float y, final RendererText.VerticalOrigin verticalOrigin, final RendererText.HorizontalOrigin horizontalOrigin) {
        ThinGL.rendererText().pushGlobalScale(size / 65f);
        ThinGL.rendererText().textLine(GLOBAL_RENDER_STACK, textLine.shape(), x, y, verticalOrigin, horizontalOrigin);
        ThinGL.rendererText().popGlobalScale();
    }
}

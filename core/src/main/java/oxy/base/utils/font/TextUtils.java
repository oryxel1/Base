package oxy.base.utils.font;

import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.gl.renderer.impl.RendererText;
import net.raphimc.thingl.text.TextLine;
import net.raphimc.thingl.text.TextRun;
import net.raphimc.thingl.text.shaping.ShapedTextLine;
import net.raphimc.thingl.text.shaping.ShapedTextRun;

import static oxy.base.utils.thingl.ThinGLUtils.GLOBAL_RENDER_STACK;

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
        GLOBAL_RENDER_STACK.pushMatrix();
        GLOBAL_RENDER_STACK.scale(size / 65f);
        ThinGL.rendererText().textRun(GLOBAL_RENDER_STACK, run, x / (size / 65f), y / (size / 65f));
        GLOBAL_RENDER_STACK.popMatrix();
    }

    public static void textRun(float size, final TextRun run, float x, float y, RendererText.VerticalOrigin verticalOrigin, RendererText.HorizontalOrigin horizontalOrigin) {
        GLOBAL_RENDER_STACK.pushMatrix();
        GLOBAL_RENDER_STACK.scale(size / 65f);
        ThinGL.rendererText().textRun(GLOBAL_RENDER_STACK, run, x / (size / 65f), y / (size / 65f), verticalOrigin, horizontalOrigin);
        GLOBAL_RENDER_STACK.popMatrix();
    }

    public static void textLine(float size, final TextLine textLine, final float x, final float y) {
        GLOBAL_RENDER_STACK.pushMatrix();
        GLOBAL_RENDER_STACK.scale(size / 65f);
        ThinGL.rendererText().textLine(GLOBAL_RENDER_STACK, textLine.shape(), x / (size / 65f), y / (size / 65f));
        GLOBAL_RENDER_STACK.popMatrix();
    }

    public static void textLine(float size, final TextLine textLine, final float x, final float y, final RendererText.VerticalOrigin verticalOrigin, final RendererText.HorizontalOrigin horizontalOrigin) {
        GLOBAL_RENDER_STACK.pushMatrix();
        GLOBAL_RENDER_STACK.scale(size / 65f);
        ThinGL.rendererText().textLine(GLOBAL_RENDER_STACK, textLine.shape(), x / (size / 65f), y / (size / 65f), verticalOrigin, horizontalOrigin);
        GLOBAL_RENDER_STACK.popMatrix();
    }
}

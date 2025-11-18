package oxy.bascenario.utils;

import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.util.DefaultGLStates;
import org.joml.Matrix4fStack;
import org.lwjgl.opengl.GL11C;

public final class ThinGLUtils {
    public static Matrix4fStack GLOBAL_RENDER_STACK;

    public static void render(final Runnable runnable) {
        ThinGL.globalUniforms().getViewMatrix().pushMatrix().identity();
        ThinGL.globalUniforms().getProjectionMatrix().pushMatrix().setOrtho(0F, 1920, 1080, 0F, -1000F, 1000F);
        DefaultGLStates.push();
        ThinGL.glStateStack().disable(GL11C.GL_DEPTH_TEST);

        runnable.run();

        ThinGL.glStateStack().enable(GL11C.GL_DEPTH_TEST);
        DefaultGLStates.pop();
        ThinGL.globalUniforms().getProjectionMatrix().popMatrix();
        ThinGL.globalUniforms().getViewMatrix().popMatrix();
    }

    public static void start() {
        final int width = ThinGL.windowInterface().getFramebufferWidth();
        final int height = ThinGL.windowInterface().getFramebufferHeight();

        ThinGL.globalUniforms().getViewMatrix().pushMatrix().identity();
        ThinGL.globalUniforms().getProjectionMatrix().pushMatrix().setOrtho(0F, width, height, 0F, -1000F, 1000F);
        DefaultGLStates.push();
        ThinGL.glStateStack().disable(GL11C.GL_DEPTH_TEST);
    }

    public static void end() {
        ThinGL.glStateStack().enable(GL11C.GL_DEPTH_TEST);
        DefaultGLStates.pop();
        ThinGL.globalUniforms().getProjectionMatrix().popMatrix();
        ThinGL.globalUniforms().getViewMatrix().popMatrix();
    }

    public static void blurRectangle(float x, float y, float width, float height, int strength) {
        ThinGL.programs().getGaussianBlur().bindInput();
        ThinGL.renderer2D().filledRectangle(GLOBAL_RENDER_STACK, x, y, x + width, y + height, Color.WHITE);
        ThinGL.programs().getGaussianBlur().unbindInput();
        ThinGL.programs().getGaussianBlur().configureParameters(strength);
        ThinGL.programs().getGaussianBlur().render(x, y, x + width, y + height);
        ThinGL.programs().getGaussianBlur().clearInput();
    }
}

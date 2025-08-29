package com.bascenario.util.render;

import com.bascenario.render.manager.TextureManager;
import com.bascenario.util.math.MathUtil;
import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.resource.image.texture.Texture2D;
import net.raphimc.thingl.util.DefaultGLStates;
import org.joml.Matrix4fStack;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11C;

import java.io.File;

public class RenderUtil {
    public static void render(final Runnable runnable) {
        final int width = ThinGL.windowInterface().getFramebufferWidth();
        final int height = ThinGL.windowInterface().getFramebufferHeight();
        ThinGL.globalUniforms().getViewMatrix().pushMatrix().identity();
        ThinGL.globalUniforms().getProjectionMatrix().pushMatrix().setOrtho(0F, width, height, 0F, -1000F, 1000F);
        DefaultGLStates.push();
        ThinGL.glStateStack().disable(GL11C.GL_DEPTH_TEST);
        runnable.run();
        ThinGL.glStateStack().enable(GL11C.GL_DEPTH_TEST);
        DefaultGLStates.pop();
        ThinGL.globalUniforms().getProjectionMatrix().popMatrix();
        ThinGL.globalUniforms().getViewMatrix().popMatrix();
    }

    public static void blurRectangle(final Matrix4fStack positionMatrix, float x, float y, float width, float height, int strength) {
        ThinGL.programs().getGaussianBlur().bindInput();
        ThinGL.renderer2D().filledRectangle(positionMatrix, x, y, x + width, y + height, Color.WHITE);
        ThinGL.programs().getGaussianBlur().unbindInput();
        ThinGL.programs().getGaussianBlur().configureParameters(strength);
        ThinGL.programs().getGaussianBlur().render(x, y, x + width, y + height);
        ThinGL.programs().getGaussianBlur().clearInput();
    }

    public static void renderBackground(final Matrix4fStack positionMatrix, float width, float height, File path) {
        renderBackground(positionMatrix, width, height, path, Color.WHITE);
    }

    public static void renderBackground(final Matrix4fStack positionMatrix, float width, float height, String path) {
        renderBackground(positionMatrix, width, height, path, Color.WHITE);
    }

    public static void renderBackground(final Matrix4fStack positionMatrix, float width, float height, File path, Color color) {
        renderBackground(positionMatrix, width, height, TextureManager.getInstance().getTexture(path), color);
    }

    public static void renderBackground(final Matrix4fStack positionMatrix, float width, float height, String path, Color color) {
        renderBackground(positionMatrix, width, height, TextureManager.getInstance().getTexture(path), color);
    }

    public static void renderBorder(final Matrix4fStack positionMatrix, float width, float height, String path, Color color) {
        ThinGL.renderer2D().coloredTexture(positionMatrix, TextureManager.getInstance().getTexture(path), 0, 0, width, height, color);
    }

    public static void renderBackground(final Matrix4fStack positionMatrix, float width, float height, Texture2D texture2D, Color color) {
        Vector4f vec = MathUtil.findBackgroundRender(new Vector2f(width, height), new Vector2f(1280, 900));
        ThinGL.renderer2D().coloredTexture(positionMatrix, texture2D, vec.x, vec.y, vec.z, vec.w, color);
    }
}

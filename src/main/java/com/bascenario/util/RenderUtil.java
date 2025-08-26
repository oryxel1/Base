package com.bascenario.util;

import com.bascenario.render.manager.TextureManager;
import com.bascenario.util.render.MathUtil;
import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.resource.image.texture.Texture2D;
import net.raphimc.thingl.text.TextRun;
import org.joml.Matrix4fStack;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.io.File;

public class RenderUtil {
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

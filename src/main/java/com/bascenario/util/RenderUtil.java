package com.bascenario.util;

import com.bascenario.render.manager.TextureManager;
import com.bascenario.util.render.MathUtil;
import imgui.ImColor;
import imgui.ImGui;
import imgui.ImVec2;
import imgui.ImVec4;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;

public class RenderUtil {
    public static void renderBackground(int width, int height, File path) {
        renderBackground(width, height, path, ImColor.rgb(255, 255, 255));
    }

    public static void renderBackground(int width, int height, File path, int color) {
        ImVec4 vec = MathUtil.findBackgroundRender(new ImVec2(width, height), new ImVec2(1280, 900));
        ImGui.getForegroundDrawList().addImage(TextureManager.getInstance().getTexture(path), new ImVec2(vec.x, vec.y), new ImVec2(vec.z, vec.w),
                new ImVec2(0, 0), new ImVec2(1, 1), color);
    }

    public static void renderBackground(int width, int height, String path) {
        renderBackground(width, height, path, ImColor.rgb(255, 255, 255));
    }

    public static void renderBackground(int width, int height, String path, int color) {
        ImGui.getForegroundDrawList().addImage(TextureManager.getInstance().getTexture(path), new ImVec2(0, 0), new ImVec2(width, height), new ImVec2(0, 0), new ImVec2(1, 1), color);
    }

    // https://github.com/FlorianMichael/fabric-imgui-example-mod/blob/1.21.8/src/main/java/de/florianmichael/imguiexample/imgui/ImGuiImpl.java
    public static int fromBufferedImage(BufferedImage image) {
        final int[] pixels = new int[image.getWidth() * image.getHeight()];
        image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

        final ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4);

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                final int pixel = pixels[y * image.getWidth() + x];

                buffer.put((byte) ((pixel >> 16) & 0xFF));
                buffer.put((byte) ((pixel >> 8) & 0xFF));
                buffer.put((byte) (pixel & 0xFF));
                buffer.put((byte) ((pixel >> 24) & 0xFF));
            }
        }

        buffer.flip();

        final int texture = GL11.glGenTextures();
        GL11.glBindTexture(3553, texture);

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);

        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);

        return texture;
    }
}

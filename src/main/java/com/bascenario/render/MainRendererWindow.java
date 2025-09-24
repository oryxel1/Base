package com.bascenario.render;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Graphics;
import com.bascenario.managers.AudioManager;
import com.bascenario.render.api.Screen;
import com.bascenario.util.render.FontUtil;
import com.bascenario.util.render.RenderUtil;
import imgui.*;
import imgui.extension.implot.ImPlot;
import imgui.flag.ImGuiConfigFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import lombok.Getter;
import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.implementation.window.GLFWWindowInterface;
import net.raphimc.thingl.wrapper.GLStateManager;
import org.apache.commons.io.IOUtils;
import org.joml.Matrix4fStack;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL32;

import java.util.Objects;

public class MainRendererWindow extends ApplicationAdapter {
    protected ImGuiImplGlfw imGuiGlfw = new ImGuiImplGlfw();
    protected ImGuiImplGl3 imGuiGl3 = new ImGuiImplGl3();

    @Getter
    private Screen currentScreen;

    public void setCurrentScreen(Screen currentScreen) {
        if (this.currentScreen != null && this.currentScreen != currentScreen) {
            this.currentScreen.dispose();
        }

        this.currentScreen = currentScreen;
    }

    public MainRendererWindow(Screen screen) {
        this.currentScreen = screen;
    }

    private double mouseX, mouseY;
    @Override
    public void create() {
        long windowHandle = ((Lwjgl3Graphics)Gdx.graphics).getWindow().getWindowHandle();

        new ThinGL(new GLFWWindowInterface(windowHandle)) {
            @Override
            protected GLStateManager createGLStateManager() {
                return new GLStateManager();
            }
        };
        ThinGL.config().setRestoreVertexArrayBinding(true);
        ThinGL.config().setRestoreProgramBinding(true);

        ThinGL.windowInterface().addFramebufferResizeCallback((width, height) -> {
            if (this.currentScreen != null) {
                this.currentScreen.init();
            }
        });
        GLFW.glfwSetCursorPosCallback(windowHandle, (window, x, y) -> {
            if (window != windowHandle) {
                return;
            }

            this.mouseX = x;
            this.mouseY = y;
        });
        GLFW.glfwSetMouseButtonCallback(windowHandle, (window, button, action, mode) -> {
            if (window != windowHandle || this.currentScreen == null) {
                return;
            }

            if (action == 1) {
                this.currentScreen.mouseClicked(mouseX, mouseY, button);
            } else {
                this.currentScreen.mouseRelease();
            }
        });
        FontUtil.loadFonts();

        ImGui.createContext();
        ImPlot.createContext();

        final ImGuiIO data = ImGui.getIO();
        data.setIniFilename("base.imgui");
        data.setFontGlobalScale(1F);
        data.setConfigFlags(ImGuiConfigFlags.DockingEnable);

        {
            final ImFontAtlas fonts = data.getFonts();
            final ImFontGlyphRangesBuilder rangesBuilder = new ImFontGlyphRangesBuilder();

            rangesBuilder.addRanges(data.getFonts().getGlyphRangesDefault());
            rangesBuilder.addRanges(data.getFonts().getGlyphRangesCyrillic());
            rangesBuilder.addRanges(data.getFonts().getGlyphRangesJapanese());

            final short[] glyphRanges = rangesBuilder.buildRanges();

            try {
                data.setFontDefault(fonts.addFontFromMemoryTTF(IOUtils.toByteArray(Objects.requireNonNull(FontUtil.class.getResourceAsStream("/assets/base/fonts/NotoSans-Regular.ttf"))), 17.5F, new ImFontConfig(), glyphRanges));
            } catch (Exception ignored) {}
        }

        imGuiGlfw.init(windowHandle, true);
        imGuiGl3.init();
    }

    @Override
    public void render() {
        AudioManager.getInstance().tickFadeIn();
        AudioManager.getInstance().tickFadeOut();

        GL32.glClearColor(1, 1, 1, 1);
        GL32.glClear(GL32.GL_COLOR_BUFFER_BIT | GL32.GL_DEPTH_BUFFER_BIT);

        imGuiGl3.newFrame();
        imGuiGlfw.newFrame();
        ImGui.newFrame();

        final Matrix4fStack positionMatrix = new Matrix4fStack(8);

        RenderUtil.render(() -> ThinGL.renderer2D().filledRectangle(positionMatrix, 0, 0, ThinGL.windowInterface().getFramebufferWidth(), ThinGL.windowInterface().getFramebufferHeight(), Color.BLACK));

        ThinGL.get().onFrameBegin();
        ThinGL.get().onFrameStart();

        if (this.currentScreen != null) {
            if (!this.currentScreen.isInit()) {
                this.currentScreen.init();
                this.currentScreen.setInit(true);
            }

            this.currentScreen.render(positionMatrix, ThinGL.windowInterface(), this.mouseX, this.mouseY);
        }

//        ImGui.showDemoWindow();

        ImGui.render();
        imGuiGl3.renderDrawData(ImGui.getDrawData());

        ThinGL.get().onFrameFinished();
        ThinGL.get().onFrameEnd();
    }

    @Override
    public void dispose() {
        if (this.currentScreen != null) {
            this.currentScreen.dispose();
        }

        imGuiGlfw.shutdown();
        imGuiGl3.shutdown();
        ImPlot.destroyContext();
        ImGui.destroyContext();
    }
}

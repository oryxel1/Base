package oxy.bascenario;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Graphics;
import com.badlogic.gdx.utils.ScreenUtils;
import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.extension.implot.ImPlot;
import imgui.flag.ImGuiConfigFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import lombok.RequiredArgsConstructor;
import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.gl.wrapper.GLStateManager;
import net.raphimc.thingl.implementation.window.GLFWWindowInterface;
import org.joml.Matrix4fStack;
import org.lwjgl.glfw.GLFW;
import oxy.bascenario.managers.AudioManager;
import oxy.bascenario.screens.ScenarioScreen;
import oxy.bascenario.utils.*;
import oxy.bascenario.utils.font.FontUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RequiredArgsConstructor
public final class EngineRenderer extends Game {
    private final ImGuiImplGlfw imGuiGlfw = new ImGuiImplGlfw();
    private final ImGuiImplGl3 imGuiGl3 = new ImGuiImplGl3();

    private final Screen initialScreen;

    private double mouseX, mouseY;
    @Override
    public void create() {
        long windowHandle = ((Lwjgl3Graphics) Gdx.graphics).getWindow().getWindowHandle();

        // Setup ThinGL, since we use this library for the main rendering logic.
        new ThinGL(new GLFWWindowInterface(windowHandle)) {
            @Override
            protected GLStateManager createGLStateManager() {
                return new GLStateManager();
            }
        };
        ThinGL.config().setRestoreVertexArrayBinding(true);
        ThinGL.config().setRestoreProgramBinding(true);

        GLFW.glfwSetCursorPosCallback(windowHandle, (window, x, y) -> {
            if (window != windowHandle) {
                return;
            }

            this.mouseX = x;
            this.mouseY = y;
        });
        GLFW.glfwSetMouseButtonCallback(windowHandle, (window, button, action, mode) -> {
            if (window != windowHandle || !(this.screen instanceof ExtendableScreen extendableScreen)) {
                return;
            }

            if (action == 1) {
                float x = ThinGL.windowInterface().getFramebufferWidth() / 1920F;
                float y = ThinGL.windowInterface().getFramebufferHeight() / 1080f;

                extendableScreen.mouseClicked(this.mouseX / x, this.mouseY / y, button);
            } else {
                extendableScreen.mouseRelease();
            }
        });

        ImGui.createContext();
        ImPlot.createContext();
        final ImGuiIO data = ImGui.getIO();
        data.setIniFilename("base.imgui");
        data.setFontGlobalScale(1F);
        data.setConfigFlags(ImGuiConfigFlags.DockingEnable);
        imGuiGlfw.init(windowHandle, true);
        imGuiGl3.init();

        if (!new File("base.imgui").exists()) {
            try {
                ImGui.loadIniSettingsFromMemory(new String(EngineRenderer.class.getResourceAsStream("/assets/base/base.imgui").readAllBytes()));
            } catch (IOException ignored) {
            }

        }

        FontUtils.loadFonts();

        this.setScreen(this.initialScreen);
    }

    @Override
    public void setScreen(Screen screen) {
        ScenarioScreen.RENDER_WITHIN_IMGUI = !(screen instanceof ScenarioScreen);
        TimeUtils.fakeTimeMillis = null;
        super.setScreen(screen);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0, 1, true);

        AudioManager.getInstance().tick();

        ThinGLUtils.GLOBAL_RENDER_STACK = new Matrix4fStack(8);
        float x = ThinGL.windowInterface().getFramebufferWidth() / 1920F;
        ThinGLUtils.GLOBAL_RENDER_STACK.scale(x, ThinGL.windowInterface().getFramebufferHeight() / 1080F, x);

        ImGuiUtils.COUNTER = 0;
        imGuiGl3.newFrame();
        imGuiGlfw.newFrame();
        ImGui.newFrame();

        super.render();

        ImGui.render();
        imGuiGl3.renderDrawData(ImGui.getDrawData());
    }

    @Override
    public void dispose() {
        super.dispose();
        imGuiGlfw.shutdown();
        imGuiGl3.shutdown();
        ImPlot.destroyContext();
        ImGui.destroyContext();
    }
}

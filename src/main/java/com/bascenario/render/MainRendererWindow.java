package com.bascenario.render;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Graphics;
import com.bascenario.render.api.Screen;
import com.bascenario.util.render.FontUtil;
import com.bascenario.util.render.RenderUtil;
import lombok.Getter;
import lombok.Setter;
import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.implementation.window.GLFWWindowInterface;
import net.raphimc.thingl.wrapper.GLStateManager;
import org.joml.Matrix4fStack;
import org.lwjgl.glfw.GLFW;

public class MainRendererWindow extends ApplicationAdapter {
    @Getter @Setter
    private Screen currentScreen;

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
//
        FontUtil.loadFonts();
    }

    @Override
    public void render() {
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

        ThinGL.get().onFrameFinished();
        ThinGL.get().onFrameEnd();
    }

    @Override
    public void dispose() {
        if (this.currentScreen != null) {
            this.currentScreen.dispose();
        }
    }
}

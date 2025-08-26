package com.bascenario.render;

import com.bascenario.render.api.Screen;
import com.bascenario.util.render.FontUtil;
import com.bascenario.util.render.WindowUtil;
import lombok.Getter;
import lombok.Setter;
import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.implementation.application.GLFWApplicationRunner;
import org.joml.Matrix4fStack;
import org.lwjgl.glfw.GLFW;

import static org.lwjgl.glfw.GLFW.GLFW_DONT_CARE;

public class MainRendererWindow extends GLFWApplicationRunner {
    @Setter
    @Getter
    private Screen currentScreen;

    private double mouseX, mouseY;

    public MainRendererWindow(Screen screen) {
        super(new Configuration().setWindowTitle("Blue Archive Scenario Engine").setExtendedDebugMode(true));
        this.currentScreen = screen;
    }

    public void launch() {
        super.launch();
    }

    @Override
    protected void createWindow() {
        super.createWindow();

//        WindowUtil.setFullScreen(this.window, true);

        GLFW.glfwSetWindowSizeLimits(this.window, 1280, 899, GLFW_DONT_CARE, GLFW_DONT_CARE);
        GLFW.glfwSetFramebufferSizeCallback(this.window, (window, width, height) -> {
            if (window != this.window) {
                return;
            }

            if (this.currentScreen != null) {
                this.currentScreen.init();
            }
        });
        GLFW.glfwSetCursorPosCallback(this.window, (window, x, y) -> {
            if (window != this.window) {
                return;
            }

            this.mouseX = x;
            this.mouseY = y;
        });
        GLFW.glfwSetMouseButtonCallback(this.window, (window, button, action, mode) -> {
            if (window != this.window  || this.currentScreen == null) {
                return;
            }

            if (action == 1) {
                this.currentScreen.mouseClicked(mouseX, mouseY, button);
            } else {
                this.currentScreen.mouseRelease();
            }
        });
    }

    @Override
    protected void init() {
        super.init();
        FontUtil.loadFonts();
    }

    @Override
    protected void render(Matrix4fStack positionMatrix) {
        ThinGL.renderer2D().filledRectangle(positionMatrix, 0, 0, this.windowInterface.getFramebufferWidth(), this.windowInterface.getFramebufferHeight(), Color.BLACK);

        if (this.currentScreen != null) {
            if (!this.currentScreen.isInit()) {
                this.currentScreen.init();
                this.currentScreen.setInit(true);
            }

            this.currentScreen.render(positionMatrix, this.windowInterface, this.mouseX, this.mouseY);
        }
    }
}

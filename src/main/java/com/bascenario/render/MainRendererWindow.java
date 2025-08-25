package com.bascenario.render;

import com.bascenario.render.api.Screen;
import com.bascenario.render.test.ElementTestScreen;
import com.bascenario.render.util.FontUtil;
import com.bascenario.render.util.WindowUtil;
import imgui.ImGui;
import imgui.ImVec2;
import imgui.app.Application;
import imgui.app.Configuration;
import lombok.Getter;
import lombok.Setter;
import org.lwjgl.glfw.GLFW;

import java.awt.*;

import static org.lwjgl.glfw.GLFW.GLFW_DONT_CARE;

public class MainRendererWindow extends Application {
    @Setter
    @Getter
    private Screen currentScreen;

    private int width, height;
    private double mouseX, mouseY;

    @Override
    protected void configure(Configuration config) {
        config.setTitle("Blue Archive Scenario Engine");
    }

    @Override
    protected void initImGui(Configuration config) {
        super.initImGui(config);
        FontUtil.loadFonts();
    }

    @Override
    protected void preRun() {
        // WindowUtil.setFullScreen(this.handle, false);

        GLFW.glfwSetWindowSizeLimits(handle, 1280, 899, GLFW_DONT_CARE, GLFW_DONT_CARE);
        GLFW.glfwSetFramebufferSizeCallback(this.handle, (window, width, height) -> {
            if (window != this.handle) {
                return;
            }
            this.width = width;
            this.height = height;

            if (this.currentScreen != null) {
                this.currentScreen.init();
            }
        });
        GLFW.glfwSetCursorPosCallback(this.handle, (window, x, y) -> {
            if (window != this.handle) {
                return;
            }

            this.mouseX = x;
            this.mouseY = y;
        });
        GLFW.glfwSetMouseButtonCallback(this.handle, (window, button, action, mode) -> {
            if (window != this.handle  || this.currentScreen == null) {
                return;
            }

            if (action == 1) {
                this.currentScreen.mouseClicked(mouseX, mouseY, button);
            } else {
                this.currentScreen.mouseRelease();
            }
        });

        int[] width = new int[1], height = new int[1];
        GLFW.glfwGetFramebufferSize(this.handle, width, height);
        this.width = Math.max(1, width[0]);
        this.height = Math.max(1, height[0]);

        // Test screen.
        this.currentScreen = new ElementTestScreen();
    }

    @Override
    public void process() {
        ImGui.pushFont(FontUtil.getFont("NotoSansRegular", 20));
        ImGui.getForegroundDrawList().addRectFilled(new ImVec2(0, 0), new ImVec2(width, height), Color.BLACK.getRGB());

        if (this.currentScreen != null) {
            if (!this.currentScreen.isInit()) {
                this.currentScreen.init();
                this.currentScreen.setInit(true);
            }

            this.currentScreen.width = width;
            this.currentScreen.height = height;
            this.currentScreen.render(this.mouseX, this.mouseY);
        }
        ImGui.popFont();
    }
}

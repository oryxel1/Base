package com.bascenario.render;

import com.bascenario.render.screen.api.Screen;
import imgui.ImGui;
import imgui.ImVec2;
import imgui.app.Application;
import imgui.app.Configuration;
import org.lwjgl.glfw.GLFW;

import java.awt.*;

public class MainRendererWindow extends Application {
    private Screen currentScreen;

    private int width, height;
    private double mouseX, mouseY;

    @Override
    protected void configure(Configuration config) {
        config.setTitle("Blue Archive Scenario Engine");
    }

    @Override
    protected void preRun() {
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
            if (window != this.handle) {
                return;
            }

            if (this.currentScreen != null) {
                this.currentScreen.mouseClicked(mouseX, mouseY, button);
            }
        });

        int[] width = new int[1], height = new int[1];
        GLFW.glfwGetFramebufferSize(this.handle, width, height);
        this.width = Math.max(1, width[0]);
        this.height = Math.max(1, height[0]);

        this.currentScreen = new Screen() {
            @Override
            public void render(double mouseX, double mouseY) {
                ImGui.getForegroundDrawList().addText(new ImVec2(5, 5), Color.BLACK.getRGB(), "Hello world!");
            }
        };
    }

    @Override
    public void process() {
        ImGui.getForegroundDrawList().addRectFilled(new ImVec2(0, 0), new ImVec2(width, height), -1);

        if (currentScreen != null) {
            currentScreen.width = width;
            currentScreen.height = height;
            currentScreen.render(this.mouseX, this.mouseY);
        }
    }
}

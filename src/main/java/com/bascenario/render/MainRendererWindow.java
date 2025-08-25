package com.bascenario.render;

import com.bascenario.render.api.Screen;
import com.bascenario.render.api.components.api.Component;
import com.bascenario.render.api.components.impl.DraggableComponent;
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
        this.currentScreen = new Screen() {
            @Override
            public void init() {
                final String testString = "Hello world! If you see this and can drag me around, that means this works properly!";

                this.components.clear();
                ImVec2 size = ImGui.getFont().calcTextSizeA(13, Float.MAX_VALUE, 0, testString);

                this.components.add(new DraggableComponent(5, 5, (int) size.x * 100, (int) size.y * 100) {
                    @Override
                    public void render(int width, int height, double mouseX, double mouseY) {
                        super.render(width, height, mouseX, mouseY);
                        ImGui.getForegroundDrawList().addText(new ImVec2(x, y), Color.BLACK.getRGB(), testString);
                    }
                });
            }
        };
    }

    @Override
    public void process() {
        ImGui.getForegroundDrawList().addRectFilled(new ImVec2(0, 0), new ImVec2(width, height), -1);

        if (this.currentScreen != null) {
            if (!this.currentScreen.isInit()) {
                this.currentScreen.init();
                this.currentScreen.setInit(true);
            }

            this.currentScreen.width = width;
            this.currentScreen.height = height;
            this.currentScreen.render(this.mouseX, this.mouseY);
        }
    }
}

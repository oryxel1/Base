package com.bascenario.render.test;

import com.bascenario.launcher.Launcher;
import com.bascenario.render.MainRendererWindow;
import com.bascenario.render.api.Screen;
import com.bascenario.render.api.components.impl.base.ClickableComponent;
import com.bascenario.render.api.components.impl.base.DraggableComponent;
import com.bascenario.render.manager.TextureManager;
import imgui.ImGui;
import imgui.ImVec2;

import java.awt.*;

public class ElementTestScreen extends Screen {
    private final Screen screen;
    public boolean appear;

    public ElementTestScreen() {
        this.screen = this;

        TextureManager.getInstance().loadTexture("cool.jpg", MainRendererWindow.class.getResourceAsStream("/cool.jpg"));
    }

    @Override
    public void init() {
        final String testString = "Hello world! If you see this and can drag me around, that means this works properly!";

        this.components.clear();
        ImVec2 size = ImGui.getFont().calcTextSizeA(13, Float.MAX_VALUE, 0, testString);

        this.components.add(new DraggableComponent(5, 5, (int) size.x, (int) size.y) {
            @Override
            public void render(int width, int height, double mouseX, double mouseY) {
                super.render(width, height, mouseX, mouseY);
                ImGui.getForegroundDrawList().addText(new ImVec2(x, y), Color.BLACK.getRGB(), testString);
            }
        });

        ImVec2 size1 = ImGui.getFont().calcTextSizeA(13, Float.MAX_VALUE, 0, "Click me plz~!");
        this.components.add(new ClickableComponent(5, 100, (int) size1.x, (int) size1.y, () -> appear = true) {
            @Override
            public void render(int width, int height, double mouseX, double mouseY) {
                super.render(width, height, mouseX, mouseY);
                ImGui.getForegroundDrawList().addText(new ImVec2(x, y), Color.BLACK.getRGB(), "Click me plz~!");
            }
        });

        ImVec2 size2 = ImGui.getFont().calcTextSizeA(13, Float.MAX_VALUE, 0, "Click me too!");
        this.components.add(new ClickableComponent(5, 300, (int) size2.x, (int) size2.y, () -> Launcher.WINDOW.setCurrentScreen(new Screen() {
            @Override
            public void init() {
                ImVec2 size1 = ImGui.getFont().calcTextSizeA(13, Float.MAX_VALUE, 0, "Click me to go back!");
                this.components.add(new ClickableComponent(5, 100, (int) size1.x, (int) size1.y, () -> Launcher.WINDOW.setCurrentScreen(screen)) {
                    @Override
                    public void render(int width, int height, double mouseX, double mouseY) {
                        super.render(width, height, mouseX, mouseY);
                        ImGui.getForegroundDrawList().addText(new ImVec2(x, y), Color.BLACK.getRGB(), "Click me to go back!");
                    }
                });
            }
        })) {
            @Override
            public void render(int width, int height, double mouseX, double mouseY) {
                super.render(width, height, mouseX, mouseY);
                ImGui.getForegroundDrawList().addText(new ImVec2(x, y), Color.BLACK.getRGB(), "Click me too!");
            }
        });
    }

    @Override
    public void render(double mouseX, double mouseY) {
        ImGui.getForegroundDrawList().addImage(TextureManager.getInstance().getTexture("cool.jpg"), new ImVec2(0, 0), new ImVec2(width, height));
        super.render(mouseX, mouseY);
        if (appear) {
            ImGui.getForegroundDrawList().addText(new ImVec2(50, 50), Color.BLACK.getRGB(), "Hi there, im new here!");
        }

        ImGui.getForegroundDrawList().addCircle(new ImVec2(50, 400), 50, Color.BLACK.getRGB());
        ImGui.getForegroundDrawList().addCircle(new ImVec2(150, 400), 50, Color.BLACK.getRGB());
        ImGui.getForegroundDrawList().addCircle(new ImVec2(250, 400), 50, Color.BLACK.getRGB());
    }
}

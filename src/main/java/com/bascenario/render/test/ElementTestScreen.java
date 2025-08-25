package com.bascenario.render.test;

import com.bascenario.launcher.Launcher;
import com.bascenario.render.MainRendererWindow;
import com.bascenario.render.api.Screen;
import com.bascenario.render.api.components.impl.base.ClickableComponent;
import com.bascenario.render.api.components.impl.base.DraggableComponent;
import com.bascenario.render.manager.TextureManager;
import com.bascenario.util.render.FontUtil;
import com.bascenario.util.render.MathUtil;
import imgui.ImGui;
import imgui.ImVec2;
import imgui.ImVec4;

public class ElementTestScreen extends Screen {
    private final Screen screen;
    public boolean appear;

    public ElementTestScreen() {
        this.screen = this;
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
                ImGui.getForegroundDrawList().addText(new ImVec2(x, y), -1, testString);
            }
        });

        ImVec2 size1A = ImGui.getFont().calcTextSizeA(13, Float.MAX_VALUE, 0, "This is a really cool dialogue!");
        this.components.add(new DraggableComponent(5, 5, (int) size1A.x, (int) size1A.y) {
            @Override
            public void render(int width, int height, double mouseX, double mouseY) {
                super.render(width, height, mouseX, mouseY);
                ImGui.pushFont(FontUtil.getFont("NotoSansRegular", 30));
                ImGui.getForegroundDrawList().addText(new ImVec2(x, y), -1, "This is a really cool dialogue!");
                ImGui.popFont();
            }
        });

        ImVec2 size1 = ImGui.getFont().calcTextSizeA(13, Float.MAX_VALUE, 0, "Click me plz~!");
        this.components.add(new ClickableComponent(5, 100, (int) size1.x, (int) size1.y, () -> appear = true) {
            @Override
            public void render(int width, int height, double mouseX, double mouseY) {
                super.render(width, height, mouseX, mouseY);
                ImGui.getForegroundDrawList().addText(new ImVec2(x, y), -1, "Click me plz~!");
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
                        ImGui.getForegroundDrawList().addText(new ImVec2(x, y), -1, "Click me to go back!");
                    }
                });
            }
        })) {
            @Override
            public void render(int width, int height, double mouseX, double mouseY) {
                super.render(width, height, mouseX, mouseY);
                ImGui.getForegroundDrawList().addText(new ImVec2(x, y), -1, "Click me too!");
            }
        });
    }

    @Override
    public void render(double mouseX, double mouseY) {
        ImGui.getForegroundDrawList().addText(new ImVec2(50, 50), -1, "Test font!");

        ImVec4 vec = MathUtil.findBackgroundRender(new ImVec2(width, height), new ImVec2(1280, 900));
        ImGui.getForegroundDrawList().addImage(TextureManager.getInstance().getTexture("/cool.jpg"),
                new ImVec2(vec.x, vec.y), new ImVec2(vec.z, vec.w));
        super.render(mouseX, mouseY);
        if (appear) {
            ImGui.getForegroundDrawList().addText(new ImVec2(50, 50), -1, "Hi there, im new here!");
        }

        ImGui.getForegroundDrawList().addCircle(new ImVec2(50, 400), 50, -1);
        ImGui.getForegroundDrawList().addCircle(new ImVec2(150, 400), 50, -1);
        ImGui.getForegroundDrawList().addCircle(new ImVec2(250, 400), 50, -1);
    }
}

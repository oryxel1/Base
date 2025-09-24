package com.bascenario.render.test;

import com.bascenario.Launcher;
import com.bascenario.render.api.Screen;
import com.bascenario.render.api.components.impl.base.ClickableComponent;
import com.bascenario.render.api.components.impl.base.DraggableComponent;
import com.bascenario.util.render.RenderUtil;
import com.bascenario.util.render.FontUtil;
import imgui.ImGui;
import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.implementation.window.WindowInterface;
import net.raphimc.thingl.text.TextRun;
import org.joml.Matrix4fStack;

// For testing rendering component in ThinGL + ImGui.
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
        TextRun helloWorldText = TextRun.fromString(FontUtil.getFont("NotoSansRegular", 13), testString);

        float helloWorldWidth = ThinGL.rendererText().getExactWidth(helloWorldText.shape());
        float helloWorldHeight = ThinGL.rendererText().getExactHeight(helloWorldText.shape());

        this.components.add(new DraggableComponent(5, 5, helloWorldWidth, helloWorldHeight) {
            @Override
            public void render(Matrix4fStack positionMatrix, WindowInterface window, double mouseX, double mouseY) {
                super.render(positionMatrix, window, mouseX, mouseY);

                ThinGL.rendererText().textRun(positionMatrix, helloWorldText, x, y);
            }
        });

        TextRun clickMePlz = TextRun.fromString(FontUtil.getFont("NotoSansRegular", 15), "Click me plz~!");
        this.components.add(new ClickableComponent(5, 100,
                ThinGL.rendererText().getExactWidth(clickMePlz.shape()),
                ThinGL.rendererText().getExactHeight(clickMePlz.shape()),
                () -> appear = !appear) {
            @Override
            public void render(Matrix4fStack positionMatrix, WindowInterface window, double mouseX, double mouseY) {
                super.render(positionMatrix, window, mouseX, mouseY);
                ThinGL.rendererText().textRun(positionMatrix, clickMePlz, x, y);
            }
        });

        TextRun clickMeToo = TextRun.fromString(FontUtil.getFont("NotoSansRegular", 15), "Click me too!");
        this.components.add(new ClickableComponent(5, 300,
                ThinGL.rendererText().getExactWidth(clickMeToo.shape()),
                ThinGL.rendererText().getExactHeight(clickMeToo.shape()),
                () -> Launcher.WINDOW.setCurrentScreen(new Screen() {
            @Override
            public void init() {
                TextRun clickMe = TextRun.fromString(FontUtil.getFont("NotoSansRegular", 15), "Click me to go back!");
                this.components.add(new ClickableComponent(5, 100,
                        ThinGL.rendererText().getExactWidth(clickMe.shape()),
                        ThinGL.rendererText().getExactHeight(clickMe.shape()),
                        () -> Launcher.WINDOW.setCurrentScreen(screen)) {
                    @Override
                    public void render(Matrix4fStack positionMatrix, WindowInterface window, double mouseX, double mouseY) {
                        super.render(positionMatrix, window, mouseX, mouseY);
                        ThinGL.rendererText().textRun(positionMatrix, clickMe, x, y);
                    }
                });
            }
        })) {
            @Override
            public void render(Matrix4fStack positionMatrix, WindowInterface window, double mouseX, double mouseY) {
                super.render(positionMatrix, window, mouseX, mouseY);
                ThinGL.rendererText().textRun(positionMatrix, clickMeToo, x, y);
            }
        });
    }

    @Override
    public void render(Matrix4fStack positionMatrix, WindowInterface window, double mouseX, double mouseY) {
        RenderUtil.render(() -> {
            RenderUtil.renderBackground(positionMatrix, window.getFramebufferWidth(), window.getFramebufferHeight(), "/cool.jpg");

            if (!this.appear) {
                RenderUtil.blurRectangle(positionMatrix, 0, 0, window.getFramebufferWidth(), window.getFramebufferHeight(), 3);
            }
        });
        ImGui.showDemoWindow();

        super.render(positionMatrix, window, mouseX, mouseY);

        RenderUtil.render(() -> {
            if (this.appear) {
                ThinGL.rendererText().textRun(positionMatrix, TextRun.fromString(FontUtil.getFont("NotoSansRegular", 15), "Yay! No more blur!"), 50, 50);
            }

            ThinGL.renderer2D().outlinedCircle(positionMatrix, 50, 400, 50, Color.WHITE, 3);
            ThinGL.renderer2D().outlinedCircle(positionMatrix, 150, 400, 50, Color.WHITE, 3);
            ThinGL.renderer2D().outlinedCircle(positionMatrix, 250, 400, 50, Color.WHITE, 3);
        });
    }
}

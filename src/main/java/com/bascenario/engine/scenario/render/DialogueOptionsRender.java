package com.bascenario.engine.scenario.render;

import com.bascenario.engine.scenario.Scenario;
import com.bascenario.render.manager.TextureManager;
import lombok.RequiredArgsConstructor;
import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.implementation.window.WindowInterface;
import org.joml.Matrix4fStack;

@RequiredArgsConstructor
public class DialogueOptionsRender {
    private final Scenario.DialogueOptions dialogueOptions;

    public void render(Matrix4fStack positionMatrix, WindowInterface window) {
        // 1337 97

        float width = window.getFramebufferWidth(), height = window.getFramebufferHeight();

        float buttonWidth = 0.69635416666f * width, buttonHeight = 0.08981481481f * height;
        float centerX = width / 2 - (buttonWidth / 2);
        ThinGL.renderer2D().texture(positionMatrix, TextureManager.getInstance().getTexture("/assets/base/uis/button.png"), centerX, 0, buttonWidth, buttonHeight);
    }
}

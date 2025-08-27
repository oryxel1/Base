package com.bascenario.engine.scenario.render;

import com.bascenario.engine.scenario.Scenario;
import com.bascenario.render.manager.TextureManager;
import com.bascenario.util.render.FontUtil;
import lombok.RequiredArgsConstructor;
import net.lenni0451.commons.animation.DynamicAnimation;
import net.lenni0451.commons.animation.easing.EasingFunction;
import net.lenni0451.commons.animation.easing.EasingMode;
import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.implementation.window.WindowInterface;
import net.raphimc.thingl.renderer.impl.RendererText;
import net.raphimc.thingl.text.TextRun;
import org.joml.Matrix4fStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class DialogueOptionsRender {
    private final Scenario.DialogueOptions dialogueOptions;

    private DynamicAnimation scaleAnimation;

    public void render(Matrix4fStack positionMatrix, WindowInterface window) {
        if (this.scaleAnimation == null) {
            this.scaleAnimation = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_IN_OUT, 130L, 0.85F);
            this.scaleAnimation.setTarget(1F);
        }
        // 1337 97
        // dis between 35, 1080

        float width = window.getFramebufferWidth(), height = window.getFramebufferHeight();

        float scale = this.scaleAnimation.getValue();

        float buttonWidth = 0.69635416666f * width * scale, buttonHeight = 0.08981481481f * height * scale;
        float centerX = width / 2 - (buttonWidth / 2);

        final Collection<String> options = this.dialogueOptions.options().keySet();

        float distanceBetween = 0.0324074074f * height;

        float totalSizeY = buttonHeight * options.size() + distanceBetween * (options.size() - 1);
        float posY = (height / 2) - (totalSizeY / 2F) - buttonHeight + (0.01387F * height);

//        System.out.println(posY);
        for (String text : options) {
            ThinGL.renderer2D().texture(positionMatrix, TextureManager.getInstance().getTexture("/assets/base/uis/button.png"),
                    centerX, posY, buttonWidth, buttonHeight);
            final TextRun textRun = TextRun.fromString(FontUtil.getFont("NotoSansRegular", Math.round(43 * scale)), text, Color.fromRGB(44, 67, 90));
            float textX = (width / 2) - (ThinGL.rendererText().getExactWidth(textRun.shape()) / 2);

            float textY = posY + (ThinGL.rendererText().getExactHeight(textRun.shape()) + buttonHeight + 0.01388888888F * height) / 2;
            ThinGL.rendererText().textRun(positionMatrix, textRun, textX, textY, RendererText.VerticalOrigin.BOTTOM, RendererText.HorizontalOrigin.LEFT);

            posY += buttonHeight + distanceBetween * scale;
        }
    }
}

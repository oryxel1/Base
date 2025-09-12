package com.bascenario.render.scenario.others;

import com.bascenario.render.scenario.ScenarioScreen;
import com.bascenario.managers.TextureManager;
import com.bascenario.util.MathUtil;
import com.bascenario.util.render.FontUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.lenni0451.commons.animation.DynamicAnimation;
import net.lenni0451.commons.animation.easing.EasingFunction;
import net.lenni0451.commons.animation.easing.EasingMode;
import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.implementation.window.WindowInterface;
import net.raphimc.thingl.resource.image.texture.Texture2D;
import net.raphimc.thingl.text.TextRun;
import org.joml.Matrix4fStack;

import java.util.Collection;
import java.util.Map;

@RequiredArgsConstructor
public class DialogueOptionsRender {
    private final ScenarioScreen screen;
    @Getter
    private final Map<String, Integer> dialogueOptions;

    private String clicked;
    private DynamicAnimation scaleAnimation;
    private DynamicAnimation flashAnimation = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_IN_OUT, 100L, 255);

    private long sinceFinished = -1;
    public boolean isFinished() {
        return this.sinceFinished > 0 && System.currentTimeMillis() - this.sinceFinished >= 10L;
    }

    public void render(Matrix4fStack positionMatrix, WindowInterface window) {
        if (this.scaleAnimation == null) {
            this.scaleAnimation = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_IN_OUT, 200L, 0.85F);
            this.scaleAnimation.setTarget(1F);
        }

        if (!this.scaleAnimation.isRunning() && !this.flashAnimation.isRunning() && this.flashAnimation.getTarget() == 0) {
            this.sinceFinished = System.currentTimeMillis();
        }

        if (this.scaleAnimation.getTarget() == 1.1F && !this.flashAnimation.isRunning() && this.clicked != null && this.scaleAnimation != null) {
            if (this.flashAnimation.getTarget() == 255 || !this.scaleAnimation.isRunning()) {
                this.flashAnimation.setTarget(0);
            } else if (this.flashAnimation.getTarget() == 0 && this.scaleAnimation.isRunning()) {
                this.flashAnimation.setTarget(255);
            }
        }

        final Collection<String> options = this.dialogueOptions.keySet();
        // 1337 97
        // dis between 35, 1080

        float width = window.getFramebufferWidth(), height = window.getFramebufferHeight();

        float scale = this.scaleAnimation.getTarget() == 1 ? this.scaleAnimation.getValue() : 1;

        float buttonWidth = 0.69635416666f * width * scale, buttonHeight = 0.08981481481f * height * scale;
        float centerX = width / 2 - (buttonWidth / 2);

        float distanceBetween = 0.0324074074f * height;

        float totalSizeY = buttonHeight * options.size() + distanceBetween * (options.size() - 1);
        float posY = (height / 2) - (totalSizeY / 2F) - buttonHeight + (0.01387F * height);

        float extraY = 0;
        for (String text : options) {
            float clonedX = centerX, clonedY = posY + extraY, clonedWidth = buttonWidth, clonedHeight = buttonHeight;
            int fontSize = Math.round((0.02866666666F * ((width + height) / 2)) * scale);
            Color color = Color.WHITE, textColor = Color.fromRGB(44, 67, 90);
            if (text.equals(this.clicked)) {
                float scale1 = this.scaleAnimation.getValue();
                clonedWidth *= scale1;
                clonedHeight *= scale1;

                clonedX = width / 2 - (clonedWidth / 2);

                float totalSizeY1 = buttonHeight + buttonHeight * (options.size() - 1) + distanceBetween * (options.size() - 1);
                clonedY = (height / 2) - (totalSizeY1 / 2F) - buttonHeight + (0.01387F * height) + extraY;

                fontSize = Math.round((0.02866666666F * scale1 * ((width + height) / 2)));

                color = Color.fromRGBA(255, 255, 255, Math.round(this.flashAnimation.getValue()));
                textColor = Color.fromRGBA(44, 67, 90, Math.round(this.flashAnimation.getValue()));
            }

            final Texture2D texture2D = TextureManager.getInstance().getTexture("/assets/base/uis/buttons/button.png");
//            ThinGL.renderer2D().coloredTexture(positionMatrix, texture2D, clonedX + 2, clonedY + 5, clonedWidth, clonedHeight,
//                    Color.fromRGBA(0, 0, 0, 30));
            ThinGL.renderer2D().coloredTexture(positionMatrix, texture2D,
                    clonedX, clonedY, clonedWidth, clonedHeight, color);

            // I'm struggling here, what the font size and type, or is that the dialogue button use a different font.
            // My recreation was really close but now the only problem is the text is about 3-4 px thicker, which is weird
            // But the font size is about the sameish, maybe NotoSansThin/Light with bold?
            TextRun textRun = TextRun.fromString(FontUtil.getFont("NotoSansRegular", fontSize), text, textColor);
            if (ThinGL.rendererText().getExactWidth(textRun.shape()) > clonedWidth - 100) {
                float diff = ThinGL.rendererText().getExactWidth(textRun.shape()) / (clonedWidth - 100);
                fontSize = MathUtil.floor(fontSize / diff);
            }
            textRun = TextRun.fromString(FontUtil.getFont("NotoSansRegular", fontSize), text, textColor);

            float textX = (width / 2) - (ThinGL.rendererText().getExactWidth(textRun.shape()) / 2);

            float textY = clonedY + (clonedHeight / 2) - (ThinGL.rendererText().getExactHeight(textRun.shape()) / 2);
            ThinGL.rendererText().textRun(positionMatrix, textRun, textX, textY);

            extraY += buttonHeight + distanceBetween * scale;
        }
    }

    public void mouseRelease() {
        if (this.clicked != null && this.scaleAnimation != null && this.scaleAnimation.getTarget() == 0.9F) {
            this.flashAnimation = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_IN_OUT, 100L, 255);
            this.scaleAnimation = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_IN_OUT, 250L, this.scaleAnimation.getValue());

            this.scaleAnimation.setTarget(1.1F);
            this.flashAnimation.setTarget(0);
        }
    }

    public void mouseClicked(WindowInterface window, double mouseX, double mouseY, int button) {
        if (this.scaleAnimation.getValue() != 1) {
            return;
        }

        float width = window.getFramebufferWidth(), height = window.getFramebufferHeight();

        float scale = this.scaleAnimation.getValue();

        float buttonWidth = 0.69635416666f * width * scale, buttonHeight = 0.08981481481f * height * scale;
        float centerX = width / 2 - (buttonWidth / 2);

        final Collection<String> options = this.dialogueOptions.keySet();

        float distanceBetween = 0.0324074074f * height;

        float totalSizeY = buttonHeight * options.size() + distanceBetween * (options.size() - 1);
        float posY = (height / 2) - (totalSizeY / 2F) - buttonHeight + (0.01387F * height);

        for (String text : options) {
            if (mouseX >= centerX && mouseX <= centerX + buttonWidth && mouseY >= posY && mouseY <= posY + buttonHeight) {
                this.clicked = text;
                this.scaleAnimation.setTarget(0.9F);
                this.screen.setDialogueIndex(this.dialogueOptions.get(text));
                break;
            }

            posY += buttonHeight + distanceBetween * scale;
        }
    }
}

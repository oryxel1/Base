package com.bascenario.render.main.components;

import com.bascenario.managers.TextureManager;
import com.bascenario.render.api.components.impl.base.ClickableComponent;
import com.bascenario.util.MathUtil;
import com.bascenario.util.render.FontUtil;
import net.lenni0451.commons.animation.DynamicAnimation;
import net.lenni0451.commons.animation.easing.EasingFunction;
import net.lenni0451.commons.animation.easing.EasingMode;
import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.implementation.window.WindowInterface;
import net.raphimc.thingl.resource.image.texture.Texture2D;
import net.raphimc.thingl.text.TextRun;
import org.joml.Matrix4fStack;

public class TitleScreenButton extends ClickableComponent {
    private final String text;

    private final DynamicAnimation hoverY;
    public TitleScreenButton(String text, float x, float y, float width, float height, Runnable action) {
        super(x, y, width, height, action);
        this.text = text;
        this.hoverY = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_IN_OUT, 200L, y);
    }

    @Override
    public void render(Matrix4fStack positionMatrix, WindowInterface window, double mouseX, double mouseY) {
        final Texture2D texture2D = TextureManager.getInstance().getTexture("/assets/base/uis/buttons/button.png");

        if (this.isHoveringOver(mouseX, mouseY, this.width, this.height)) {
            this.hoverY.setTarget(this.y - 0.009F * window.getFramebufferHeight());
        } else {
            this.hoverY.setTarget(this.y);
        }

        ThinGL.renderer2D().coloredTexture(positionMatrix, texture2D, this.x, this.hoverY.getValue(), this.width, this.height, Color.WHITE);

        final Color textColor = Color.fromRGB(44, 67, 90);
        int fontSize = Math.round((0.02866666666F * ((window.getFramebufferWidth() + window.getFramebufferHeight()) / 2F)) * 1);
        TextRun textRun = TextRun.fromString(FontUtil.getFont("NotoSansRegular", fontSize), text, textColor);
        if (ThinGL.rendererText().getExactWidth(textRun.shape()) > width - 100) {
            float diff = ThinGL.rendererText().getExactWidth(textRun.shape()) / (width - 100);
            fontSize = MathUtil.floor(fontSize / diff);
        }
        textRun = TextRun.fromString(FontUtil.getFont("NotoSansRegular", fontSize), text, textColor);

        float textX = (width / 2F) - (ThinGL.rendererText().getExactWidth(textRun.shape()) / 2);

        float textY = this.hoverY.getValue() + (height / 2F) - (ThinGL.rendererText().getExactHeight(textRun.shape()) / 2);
        ThinGL.rendererText().textRun(positionMatrix, textRun, x + textX, textY);
    }
}

package com.bascenario.engine.scenario.event.impl;

import com.bascenario.engine.scenario.event.api.Event;
import com.bascenario.engine.scenario.screen.ScenarioScreen;
import com.bascenario.render.manager.TextureManager;
import com.bascenario.util.render.FontUtil;
import net.lenni0451.commons.animation.DynamicAnimation;
import net.lenni0451.commons.animation.easing.EasingFunction;
import net.lenni0451.commons.animation.easing.EasingMode;
import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.implementation.window.WindowInterface;
import net.raphimc.thingl.text.TextRun;
import net.raphimc.thingl.text.font.Font;
import org.joml.Matrix4fStack;

public class LocationInfoEvent extends Event {
    private final String location;

    private final DynamicAnimation popupFade = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_IN_OUT, 500L, 0);
    private final DynamicAnimation textFade = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_IN_OUT, 500L, 0);

    public LocationInfoEvent(String location) {
        super(4000L);
        this.location = location;
    }

    @Override
    public void render(ScenarioScreen screen, long time, Matrix4fStack positionMatrix, WindowInterface window) {
        if (this.popupFade.getTarget() == 0 && time < 1000L) {
            this.popupFade.setTarget(200);
            this.textFade.setTarget(255);
        } else if (this.popupFade.getTarget() == 200 && time >= 3000L) {
            this.popupFade.setTarget(0);
            this.textFade.setTarget(0);
        }

        float width = window.getFramebufferWidth(), height = window.getFramebufferHeight();
        Font font = FontUtil.getFont("NotoSansSemiBold", Math.round(0.02666666666F * ((width + height) / 2F)));

        final TextRun text = TextRun.fromString(font, this.location, Color.fromRGBA(255, 255, 255, Math.round(this.textFade.getValue())));

        float locationY = (0.18518518518F * height);
        float sizeY = 0.06F * height;
        float sizeWidth = ThinGL.rendererText().getExactWidth(text.shape()) + 85;

        Color color = Color.fromRGBA(255, 255, 255, Math.round(this.popupFade.getValue()));
        ThinGL.renderer2D().filledRectangle(
                positionMatrix, 0, locationY, sizeWidth, locationY + sizeY,
                Color.fromRGBA(85, 91, 124, Math.round(this.popupFade.getValue()))
        );
        float separatorY = locationY + (sizeY / 2) - ((sizeY / 2F) / 2);
        ThinGL.renderer2D().filledRectangle(
                positionMatrix, 30, separatorY, 35, separatorY + (sizeY / 2F),
                Color.fromRGBA(182, 182, 182, Math.round(this.popupFade.getValue()))
        );
        ThinGL.renderer2D().coloredTexture(positionMatrix, TextureManager.getInstance().getTexture("/assets/base/uis/location_popup.png"),
                sizeWidth, locationY,  30,  sizeY, color);

        ThinGL.rendererText().textRun(positionMatrix, text, 56, locationY + (sizeY / 2) - (ThinGL.rendererText().getExactHeight(text.shape()) / 2));
    }
}

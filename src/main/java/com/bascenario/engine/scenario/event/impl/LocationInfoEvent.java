package com.bascenario.engine.scenario.event.impl;

import com.bascenario.engine.scenario.event.api.Event;
import com.bascenario.render.manager.TextureManager;
import com.bascenario.util.render.FontUtil;
import imgui.ImColor;
import imgui.ImGui;
import imgui.ImVec2;
import net.lenni0451.commons.animation.DynamicAnimation;
import net.lenni0451.commons.animation.easing.EasingFunction;
import net.lenni0451.commons.animation.easing.EasingMode;

public class LocationInfoEvent extends Event {
    private final String location;

    private final DynamicAnimation popupFade = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_IN_OUT, 500L, 0);
    private final DynamicAnimation textFade = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_IN_OUT, 500L, 0);

    public LocationInfoEvent(String location) {
        super(4000L);
        this.location = location;
    }

    @Override
    public void render(long time, int width, int height) {
        if (this.popupFade.getTarget() == 0 && time < 1000L) {
            this.popupFade.setTarget(200);
            this.textFade.setTarget(255);
        } else if (this.popupFade.getTarget() == 200 && time >= 3000L) {
            this.popupFade.setTarget(0);
            this.textFade.setTarget(0);
        }

        ImGui.pushFont(FontUtil.getFont("NotoSansSemiBold", Math.round(0.04F * ((width + height) / 2F))));

        float locationY = 0.14457831325F * height;
        float sizeY = 0.06F * height;
        float sizeWidth = ImGui.calcTextSizeX(this.location) + 80;

        ImGui.getForegroundDrawList().addRectFilled(new ImVec2(0, locationY), new ImVec2(sizeWidth, locationY + sizeY), ImColor.rgba(85, 91, 124, Math.round(this.popupFade.getValue())));
        float separatorY = locationY + (sizeY / 2) - ((sizeY / 1.5F) / 2);
        ImGui.getForegroundDrawList().addRectFilled(new ImVec2(20, separatorY), new ImVec2(25, separatorY + (sizeY / 1.5F)), ImColor.rgba(119, 123, 144, Math.round(this.popupFade.getValue())));

        int color = ImColor.rgba(255, 255, 255, Math.round(this.popupFade.getValue()));
        ImGui.getForegroundDrawList().addImage(TextureManager.getInstance().getTexture("/assets/base/uis/location_popup.png"),
                new ImVec2(sizeWidth, locationY), new ImVec2(sizeWidth + 30, locationY + sizeY), new ImVec2(0, 0), new ImVec2(1, 1), color);

        ImGui.getForegroundDrawList().addText(new ImVec2(40, locationY + (sizeY / 2) - (ImGui.calcTextSizeY(this.location) / 2)), ImColor.rgba(255, 255, 255, Math.round(this.textFade.getValue())), this.location);

        ImGui.popFont();
    }
}

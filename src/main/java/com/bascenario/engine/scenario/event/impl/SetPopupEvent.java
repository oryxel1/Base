package com.bascenario.engine.scenario.event.impl;

import com.bascenario.engine.scenario.elements.PopupImage;
import com.bascenario.engine.scenario.event.api.Event;
import com.bascenario.render.scenario.ScenarioScreen;
import com.bascenario.util.FileUtil;
import com.bascenario.util.GsonUtil;
import com.bascenario.util.render.ImGuiUtil;
import com.google.gson.*;
import imgui.ImGui;

import java.lang.reflect.Type;

public class SetPopupEvent extends Event<SetPopupEvent> {
    private final PopupImage popupImage;
    public SetPopupEvent(PopupImage popupImage) {
        super(0);
        this.popupImage = popupImage;
    }

    @Override
    public void onStart(ScenarioScreen screen) {
        screen.setPopupImage(this.popupImage);
    }

    @Override
    public void renderImGui() {
        ImGui.text("Popup Image: " + this.popupImage.path());
        ImGui.sameLine();
        if (ImGui.button("Browse##" + ImGuiUtil.COUNTER++)) {
            final String path = FileUtil.pickFile("jpg", "png");
            if (!path.isBlank()) {
                this.popupImage.path(path);
            }
        }
        
        this.popupImage.path(ImGuiUtil.inputText("Popup Image", this.popupImage.path()));
        this.popupImage.duration(ImGuiUtil.sliderInt("Duration", (int) this.popupImage.duration(), 1, 10000));
    }

    @Override
    public SetPopupEvent defaultEvent() {
        return new SetPopupEvent(new PopupImage("", 1000L));
    }

    @Override
    public void serialize(JsonObject serialized) {
        serialized.add("image", GsonUtil.toJson(this.popupImage));
    }

    @Override
    public SetPopupEvent deserialize(JsonObject serialized) {
        return new SetPopupEvent(GsonUtil.getGson().fromJson(serialized.get("image"), PopupImage.class));
    }

    @Override
    public String type() {
        return "set-popup-image";
    }
}

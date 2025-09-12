package com.bascenario.engine.scenario.event.impl;

import com.bascenario.engine.scenario.elements.PopupImage;
import com.bascenario.engine.scenario.event.api.Event;
import com.bascenario.render.scenario.ScenarioScreen;
import com.bascenario.util.GsonUtil;
import com.google.gson.*;

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

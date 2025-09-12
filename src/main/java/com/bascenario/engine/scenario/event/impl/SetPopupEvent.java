package com.bascenario.engine.scenario.event.impl;

import com.bascenario.engine.scenario.elements.PopupImage;
import com.bascenario.engine.scenario.event.api.Event;
import com.bascenario.render.scenario.ScenarioScreen;
import com.bascenario.util.GsonUtil;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;

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
    public SetPopupEvent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return new SetPopupEvent(GsonUtil.getGson().fromJson(json, PopupImage.class));
    }

    @Override
    public JsonElement serialize(SetPopupEvent src, Type typeOfSrc, JsonSerializationContext context) {
        return GsonUtil.toJson(src.popupImage);
    }
}

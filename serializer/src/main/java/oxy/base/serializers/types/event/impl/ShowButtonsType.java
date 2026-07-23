package oxy.base.serializers.types.event.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import oxy.base.api.event.ShowButtonsEvent;
import oxy.base.serializers.base.TypeWithName;

public class ShowButtonsType implements TypeWithName<ShowButtonsEvent> {
    @Override
    public String type() {
        return "show-buttons";
    }

    @Override
    public JsonElement write(ShowButtonsEvent showButtonsEvent) {
        return new JsonPrimitive(showButtonsEvent.show());
    }

    @Override
    public ShowButtonsEvent read(JsonElement element) {
        return new ShowButtonsEvent(element.getAsBoolean());
    }
}

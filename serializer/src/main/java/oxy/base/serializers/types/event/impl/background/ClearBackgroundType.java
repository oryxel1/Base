package oxy.base.serializers.types.event.impl.background;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import oxy.base.api.event.background.ClearBackgroundEvent;
import oxy.base.serializers.base.TypeWithName;

public class ClearBackgroundType implements TypeWithName<ClearBackgroundEvent> {
    @Override
    public String type() {
        return "clear-background";
    }

    @Override
    public JsonElement write(ClearBackgroundEvent clearBackgroundEvent) {
        return new JsonPrimitive(clearBackgroundEvent.duration());
    }

    @Override
    public ClearBackgroundEvent read(JsonElement element) {
        return new ClearBackgroundEvent(element.getAsInt());
    }
}

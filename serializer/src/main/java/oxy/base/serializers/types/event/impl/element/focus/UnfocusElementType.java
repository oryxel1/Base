package oxy.base.serializers.types.event.impl.element.focus;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;

import oxy.base.api.event.element.focus.UnfocusElementEvent;
import oxy.base.serializers.base.TypeWithName;

public class UnfocusElementType implements TypeWithName<UnfocusElementEvent> {
    @Override
    public String type() {
        return "unfocus-element";
    }

    @Override
    public JsonElement write(UnfocusElementEvent unfocusElementEvent) {
        return JsonNull.INSTANCE;
    }

    @Override
    public UnfocusElementEvent read(JsonElement element) {
        return new UnfocusElementEvent();
    }
}

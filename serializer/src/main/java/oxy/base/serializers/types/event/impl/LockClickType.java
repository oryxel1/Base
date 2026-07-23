package oxy.base.serializers.types.event.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import oxy.base.api.event.LockClickEvent;
import oxy.base.serializers.base.TypeWithName;

public class LockClickType implements TypeWithName<LockClickEvent> {
    @Override
    public String type() {
        return "lock-click";
    }

    @Override
    public JsonElement write(LockClickEvent lockClickEvent) {
        return new JsonPrimitive(lockClickEvent.lock());
    }

    @Override
    public LockClickEvent read(JsonElement element) {
        return new LockClickEvent(element.getAsBoolean());
    }
}

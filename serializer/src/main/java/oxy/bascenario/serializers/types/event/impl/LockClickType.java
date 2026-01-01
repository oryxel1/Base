package oxy.bascenario.serializers.types.event.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import io.netty.buffer.ByteBuf;
import oxy.bascenario.api.event.LockClickEvent;
import oxy.bascenario.serializers.base.TypeWithName;

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

    @Override
    public void write(LockClickEvent lockClickEvent, ByteBuf buf) {
        buf.writeBoolean(lockClickEvent.lock());
    }

    @Override
    public LockClickEvent read(ByteBuf buf) {
        return new LockClickEvent(buf.readBoolean());
    }
}

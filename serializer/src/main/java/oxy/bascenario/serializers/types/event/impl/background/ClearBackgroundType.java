package oxy.bascenario.serializers.types.event.impl.background;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import io.netty.buffer.ByteBuf;
import oxy.bascenario.api.event.background.ClearBackgroundEvent;
import oxy.bascenario.serializers.base.TypeWithName;

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

    @Override
    public void write(ClearBackgroundEvent clearBackgroundEvent, ByteBuf buf) {
        buf.writeInt(clearBackgroundEvent.duration());
    }

    @Override
    public ClearBackgroundEvent read(ByteBuf buf) {
        return new ClearBackgroundEvent(buf.readInt());
    }
}

package oxy.bascenario.serializers.types.event.impl.element.focus;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import io.netty.buffer.ByteBuf;
import oxy.bascenario.api.event.element.focus.FocusElementEvent;
import oxy.bascenario.serializers.base.TypeWithName;

public class FocusElementType implements TypeWithName<FocusElementEvent> {
    @Override
    public String type() {
        return "focus-element";
    }

    @Override
    public JsonElement write(FocusElementEvent focusElementEvent) {
        return new JsonPrimitive(focusElementEvent.id());
    }

    @Override
    public FocusElementEvent read(JsonElement element) {
        return new FocusElementEvent(element.getAsInt());
    }

    @Override
    public void write(FocusElementEvent focusElementEvent, ByteBuf buf) {
        buf.writeInt(focusElementEvent.id());
    }

    @Override
    public FocusElementEvent read(ByteBuf buf) {
        return new FocusElementEvent(buf.readInt());
    }
}

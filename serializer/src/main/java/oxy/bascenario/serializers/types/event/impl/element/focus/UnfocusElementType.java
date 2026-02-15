package oxy.bascenario.serializers.types.event.impl.element.focus;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;
import io.netty.buffer.ByteBuf;
import oxy.bascenario.api.event.element.focus.FocusElementEvent;
import oxy.bascenario.api.event.element.focus.UnfocusElementEvent;
import oxy.bascenario.serializers.base.TypeWithName;

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

    @Override
    public void write(UnfocusElementEvent unfocusElementEvent, ByteBuf buf) {
    }

    @Override
    public UnfocusElementEvent read(ByteBuf buf) {
        return new UnfocusElementEvent();
    }
}

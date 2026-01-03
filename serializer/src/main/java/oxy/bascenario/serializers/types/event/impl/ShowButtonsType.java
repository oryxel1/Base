package oxy.bascenario.serializers.types.event.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import io.netty.buffer.ByteBuf;
import oxy.bascenario.api.event.ShowButtonsEvent;
import oxy.bascenario.serializers.base.TypeWithName;

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

    @Override
    public void write(ShowButtonsEvent showButtonsEvent, ByteBuf buf) {
        buf.writeBoolean(showButtonsEvent.show());
    }

    @Override
    public ShowButtonsEvent read(ByteBuf buf) {
        return new ShowButtonsEvent(buf.readBoolean());
    }
}

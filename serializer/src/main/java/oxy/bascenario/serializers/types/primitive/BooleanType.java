package oxy.bascenario.serializers.types.primitive;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import io.netty.buffer.ByteBuf;
import oxy.bascenario.serializers.Type;

public final class BooleanType implements Type<Boolean> {
    @Override
    public JsonElement write(Boolean aBoolean) {
        return new JsonPrimitive(aBoolean);
    }

    @Override
    public Boolean read(JsonElement object) {
        return object.getAsBoolean();
    }

    @Override
    public void write(Boolean aBoolean, ByteBuf buf) {
        buf.writeBoolean(aBoolean);
    }

    @Override
    public Boolean read(ByteBuf buf) {
        return buf.readBoolean();
    }
}

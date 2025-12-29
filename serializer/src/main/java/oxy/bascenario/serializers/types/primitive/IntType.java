package oxy.bascenario.serializers.types.primitive;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import io.netty.buffer.ByteBuf;
import oxy.bascenario.serializers.Type;

public class IntType implements Type<Integer> {
    @Override
    public JsonElement write(Integer integer) {
        return new JsonPrimitive(integer);
    }

    @Override
    public Integer read(JsonElement object) {
        return object.getAsInt();
    }

    @Override
    public void write(Integer integer, ByteBuf buf) {
        buf.writeInt(integer);
    }

    @Override
    public Integer read(ByteBuf buf) {
        return buf.readInt();
    }
}

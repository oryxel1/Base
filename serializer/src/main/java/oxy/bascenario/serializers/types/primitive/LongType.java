package oxy.bascenario.serializers.types.primitive;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import io.netty.buffer.ByteBuf;
import oxy.bascenario.serializers.Type;

public class LongType implements Type<Long> {
    @Override
    public JsonElement write(Long aLong) {
        return new JsonPrimitive(aLong);
    }

    @Override
    public Long read(JsonElement object) {
        return object.getAsLong();
    }

    @Override
    public void write(Long aLong, ByteBuf buf) {
        buf.writeLong(aLong);
    }

    @Override
    public Long read(ByteBuf buf) {
        return buf.readLong();
    }
}

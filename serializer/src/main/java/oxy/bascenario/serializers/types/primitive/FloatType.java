package oxy.bascenario.serializers.types.primitive;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import io.netty.buffer.ByteBuf;
import oxy.bascenario.serializers.Type;

public class FloatType implements Type<Float> {
    @Override
    public JsonElement write(Float aFloat) {
        return new JsonPrimitive(aFloat);
    }

    @Override
    public Float read(JsonElement object) {
        return object.getAsFloat();
    }

    @Override
    public void write(Float aFloat, ByteBuf buf) {
        buf.writeFloat(aFloat);
    }

    @Override
    public Float read(ByteBuf buf) {
        return buf.readFloat();
    }
}

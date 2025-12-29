package oxy.bascenario.serializers.types.primitive;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import io.netty.buffer.ByteBuf;
import oxy.bascenario.serializers.Type;
import oxy.bascenario.serializers.Types;

public final class StringType implements Type<String> {
    @Override
    public JsonElement write(String s) {
        return new JsonPrimitive(s);
    }

    @Override
    public String read(JsonElement object) {
        return object.getAsString();
    }

    @Override
    public void write(String s, ByteBuf buf) {
        final byte[] bytes = s.getBytes();
        Types.INT_TYPE.write(bytes.length, buf);
        buf.writeBytes(bytes);
    }

    @Override
    public String read(ByteBuf buf) {
        final byte[] array = new byte[buf.readInt()];
        buf.readBytes(array);
        return new String(array);
    }
}

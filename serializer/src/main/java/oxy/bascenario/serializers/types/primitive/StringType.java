package oxy.bascenario.serializers.types.primitive;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import io.netty.buffer.ByteBuf;
import oxy.bascenario.serializers.base.Type;

public final class StringType implements Type<String> {
    @Override
    public JsonElement write(String s) {
        return new JsonPrimitive(s);
    }

    @Override
    public String read(JsonElement object) {
        return object.getAsString();
    }
}

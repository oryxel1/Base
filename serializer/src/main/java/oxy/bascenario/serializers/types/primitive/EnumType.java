package oxy.bascenario.serializers.types.primitive;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import io.netty.buffer.ByteBuf;
import oxy.bascenario.serializers.base.Type;

public record EnumType<T extends Enum<T>>(Class<T> enumClass, T[] values) implements Type<T> {
    @Override
    public JsonElement write(T t) {
        return new JsonPrimitive(t.name());
    }

    @Override
    public T read(JsonElement element) {
        return Enum.valueOf(enumClass, element.getAsString());
    }
}

package oxy.bascenario.serializers.base;

import com.google.gson.JsonElement;
import io.netty.buffer.ByteBuf;

public interface Type<T> {
    JsonElement write(T t);
    T read(JsonElement element);

    void write(T t, ByteBuf buf);
    T read(ByteBuf buf);
}

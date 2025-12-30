package oxy.bascenario.serializers.types.primitive;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import io.netty.buffer.ByteBuf;
import oxy.bascenario.serializers.base.Type;

public record NullableType<T>(Type<T> type) implements Type<T> {
    @Override
    public JsonElement write(T t) {
        if (t == null) {
            return JsonNull.INSTANCE;
        }

        return type.write(t);
    }

    @Override
    public T read(JsonElement element) {
        if (element == null || element.isJsonNull()) {
            return null;
        }

        return type.read(element);
    }

    @Override
    public void write(T t, ByteBuf buf) {
        buf.writeBoolean(t != null);
        if (t != null) {
            type.write(t, buf);
        }
    }

    @Override
    public T read(ByteBuf buf) {
        if (!buf.readBoolean()) {
            return null;
        }

        return type.read(buf);
    }
}

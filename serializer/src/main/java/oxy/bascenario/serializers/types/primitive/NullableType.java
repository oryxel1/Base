package oxy.bascenario.serializers.types.primitive;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.netty.buffer.ByteBuf;
import oxy.bascenario.serializers.Type;

public record NullableType<T>(Type<T> type) implements Type<T> {
    @Override
    public JsonElement write(T t) {
        final JsonObject object = new JsonObject();
        object.addProperty("present", t != null);
        if (t != null) {
            object.add("value", type.write(t));
        }
        return object;
    }

    @Override
    public T read(JsonElement element) {
        final JsonObject object = element.getAsJsonObject();
        if (!object.get("present").getAsBoolean()) {
            return null;
        }

        return type.read(object.get("value"));
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

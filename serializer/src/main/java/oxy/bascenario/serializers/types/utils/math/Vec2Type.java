package oxy.bascenario.serializers.types.utils.math;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import io.netty.buffer.ByteBuf;
import oxy.bascenario.api.utils.math.Vec2;
import oxy.bascenario.serializers.Type;
import oxy.bascenario.serializers.Types;

public class Vec2Type implements Type<Vec2> {
    @Override
    public JsonElement write(Vec2 vec2) {
        final JsonArray array = new JsonArray();
        array.add(vec2.x());
        array.add(vec2.y());
        return array;
    }

    @Override
    public Vec2 read(JsonElement element) {
        final JsonArray array = element.getAsJsonArray();
        return new Vec2(array.get(0).getAsFloat(), array.get(1).getAsFloat());
    }

    @Override
    public void write(Vec2 vec2, ByteBuf buf) {
        buf.writeFloat(vec2.x());
        buf.writeFloat(vec2.y());
    }

    @Override
    public Vec2 read(ByteBuf buf) {
        return new Vec2(buf.readFloat(), buf.readFloat());
    }
}

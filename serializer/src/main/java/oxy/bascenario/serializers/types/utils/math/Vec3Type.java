package oxy.bascenario.serializers.types.utils.math;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import io.netty.buffer.ByteBuf;
import oxy.bascenario.api.utils.math.Vec3;
import oxy.bascenario.serializers.Type;
import oxy.bascenario.serializers.Types;

public class Vec3Type implements Type<Vec3> {
    @Override
    public JsonElement write(Vec3 vec3) {
        final JsonArray array = new JsonArray();
        array.add(vec3.x());
        array.add(vec3.y());
        array.add(vec3.z());
        return array;
    }

    @Override
    public Vec3 read(JsonElement element) {
        final JsonArray array = element.getAsJsonArray();
        return new Vec3(array.get(0).getAsFloat(), array.get(1).getAsFloat(), array.get(2).getAsFloat());
    }

    @Override
    public void write(Vec3 vec3, ByteBuf buf) {
        Types.FLOAT_TYPE.write(vec3.x(), buf);
        Types.FLOAT_TYPE.write(vec3.y(), buf);
        Types.FLOAT_TYPE.write(vec3.z(), buf);
    }

    @Override
    public Vec3 read(ByteBuf buf) {
        return new Vec3(buf.readFloat(), buf.readFloat(), buf.readFloat());
    }
}

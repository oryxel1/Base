package oxy.bascenario.serializers.types.utils.math;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import io.netty.buffer.ByteBuf;
import oxy.bascenario.api.utils.math.Axis;
import oxy.bascenario.serializers.Type;

public class AxisType implements Type<Axis> {
    @Override
    public JsonElement write(Axis axis) {
        return new JsonPrimitive(axis.name());
    }

    @Override
    public Axis read(JsonElement element) {
        return Axis.valueOf(element.getAsString());
    }

    @Override
    public void write(Axis axis, ByteBuf buf) {
        buf.writeInt(axis.ordinal());
    }

    @Override
    public Axis read(ByteBuf buf) {
        return Axis.values()[buf.readInt()];
    }
}

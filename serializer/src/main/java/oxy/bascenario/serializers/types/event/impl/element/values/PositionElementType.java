package oxy.bascenario.serializers.types.event.impl.element.values;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.netty.buffer.ByteBuf;
import oxy.bascenario.api.event.element.values.PositionElementEvent;
import oxy.bascenario.serializers.Types;
import oxy.bascenario.serializers.base.TypeWithName;

public class PositionElementType implements TypeWithName<PositionElementEvent> {
    @Override
    public String type() {
        return "position-element";
    }

    @Override
    public JsonElement write(PositionElementEvent event) {
        final JsonObject object = new JsonObject();
        object.addProperty("id", event.id());
        object.add("subId", Types.NULLABLE_INT.write(event.subId()));
        object.addProperty("duration", event.duration());
        object.add("vec2", Types.VECTOR_2F_TYPE.write(event.vec()));
        object.add("easing", Types.EASING_TYPE.write(event.easing()));
        object.addProperty("type", event.type().name());
        return object;
    }

    @Override
    public PositionElementEvent read(JsonElement element) {
        final JsonObject object = element.getAsJsonObject();
        return new PositionElementEvent(
                object.get("id").getAsInt(), Types.NULLABLE_INT.read(object.get("subId")), object.get("duration").getAsInt(),
                Types.VECTOR_2F_TYPE.read(object.get("vec2")), Types.EASING_TYPE.read(object.get("easing")), PositionElementEvent.Type.valueOf(object.get("type").getAsString())
        );
    }

    @Override
    public void write(PositionElementEvent event, ByteBuf buf) {
        buf.writeInt(event.id());
        Types.NULLABLE_INT.write(event.subId());
        buf.writeInt(event.duration());
        Types.VECTOR_2F_TYPE.write(event.vec(), buf);
        Types.EASING_TYPE.write(event.easing(), buf);
        buf.writeInt(event.type().ordinal());
    }

    @Override
    public PositionElementEvent read(ByteBuf buf) {
        return new PositionElementEvent(buf.readInt(), Types.NULLABLE_INT.read(buf), buf.readInt(), Types.VECTOR_2F_TYPE.read(buf), Types.EASING_TYPE.read(buf), PositionElementEvent.Type.values()[buf.readInt()]);
    }
}

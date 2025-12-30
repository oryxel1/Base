package oxy.bascenario.serializers.types.event.impl.element.values;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.netty.buffer.ByteBuf;
import oxy.bascenario.api.event.element.values.RotateElementEvent;
import oxy.bascenario.serializers.Types;
import oxy.bascenario.serializers.base.TypeWithName;

public class RotateElementType implements TypeWithName<RotateElementEvent> {
    @Override
    public String type() {
        return "rotate-element";
    }

    @Override
    public JsonElement write(RotateElementEvent event) {
        final JsonObject object = new JsonObject();
        object.addProperty("id", event.id());
        object.add("subId", Types.NULLABLE_INT.write(event.subId()));
        object.addProperty("duration", event.duration());
        object.add("rotation", Types.VECTOR_3F_TYPE.write(event.rotation()));
        object.add("easing", Types.EASING_TYPE.write(event.easing()));
        return object;
    }

    @Override
    public RotateElementEvent read(JsonElement element) {
        final JsonObject object = element.getAsJsonObject();
        return new RotateElementEvent(
                object.get("id").getAsInt(), Types.NULLABLE_INT.read(object.get("subId")), object.get("duration").getAsInt(),
                Types.VECTOR_3F_TYPE.read(object.get("rotation")), Types.EASING_TYPE.read(object.get("easing"))
        );
    }

    @Override
    public void write(RotateElementEvent event, ByteBuf buf) {
        buf.writeInt(event.id());
        Types.NULLABLE_INT.write(event.subId());
        buf.writeInt(event.duration());
        Types.VECTOR_3F_TYPE.write(event.rotation(), buf);
        Types.EASING_TYPE.write(event.easing(), buf);
    }

    @Override
    public RotateElementEvent read(ByteBuf buf) {
        return new RotateElementEvent(buf.readInt(), Types.NULLABLE_INT.read(buf), buf.readInt(), Types.VECTOR_3F_TYPE.read(buf), Types.EASING_TYPE.read(buf));
    }
}

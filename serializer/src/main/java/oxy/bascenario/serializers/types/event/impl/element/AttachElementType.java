package oxy.bascenario.serializers.types.event.impl.element;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.netty.buffer.ByteBuf;
import oxy.bascenario.api.event.element.AttachElementEvent;
import oxy.bascenario.serializers.Types;
import oxy.bascenario.serializers.base.TypeWithName;

public class AttachElementType implements TypeWithName<AttachElementEvent> {
    @Override
    public String type() {
        return "attach-element";
    }

    @Override
    public JsonElement write(AttachElementEvent attachElementEvent) {
        final JsonObject object = new JsonObject();
        object.addProperty("id", attachElementEvent.id());
        object.add("subId", Types.NULLABLE_INT.write(attachElementEvent.subId()));
        object.add("object", Types.ELEMENT_TYPE.write(attachElementEvent.element()));
        return object;
    }

    @Override
    public AttachElementEvent read(JsonElement element) {
        final JsonObject object = element.getAsJsonObject();
        return new AttachElementEvent(object.get("id").getAsInt(), Types.NULLABLE_INT.read(object.get("subId")), Types.ELEMENT_TYPE.read(object.get("object")));
    }

    @Override
    public void write(AttachElementEvent attachElementEvent, ByteBuf buf) {
        buf.writeInt(attachElementEvent.id());
        Types.NULLABLE_INT.write(attachElementEvent.subId(), buf);
        Types.ELEMENT_TYPE.write(attachElementEvent.element(), buf);
    }

    @Override
    public AttachElementEvent read(ByteBuf buf) {
        return new AttachElementEvent(buf.readInt(), Types.NULLABLE_INT.read(buf), Types.ELEMENT_TYPE.read(buf));
    }
}

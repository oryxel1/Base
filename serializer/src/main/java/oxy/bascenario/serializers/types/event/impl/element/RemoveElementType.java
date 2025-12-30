package oxy.bascenario.serializers.types.event.impl.element;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.netty.buffer.ByteBuf;
import oxy.bascenario.api.event.element.RemoveElementEvent;
import oxy.bascenario.serializers.Types;
import oxy.bascenario.serializers.base.TypeWithName;

public class RemoveElementType implements TypeWithName<RemoveElementEvent> {
    @Override
    public String type() {
        return "remove-element";
    }

    @Override
    public JsonElement write(RemoveElementEvent removeElementEvent) {
        final JsonObject object = new JsonObject();
        object.addProperty("id", removeElementEvent.id());
        object.add("subId", Types.NULLABLE_INT.write(removeElementEvent.subId()));
        return object;
    }

    @Override
    public RemoveElementEvent read(JsonElement element) {
        final JsonObject object = new JsonObject();
        return new RemoveElementEvent(object.get("id").getAsInt(), Types.NULLABLE_INT.read(object.get("subId")));
    }

    @Override
    public void write(RemoveElementEvent removeElementEvent, ByteBuf buf) {
        buf.writeInt(removeElementEvent.id());
        Types.NULLABLE_INT.write(removeElementEvent.subId(), buf);
    }

    @Override
    public RemoveElementEvent read(ByteBuf buf) {
        return new RemoveElementEvent(buf.readInt(), Types.NULLABLE_INT.read(buf));
    }
}

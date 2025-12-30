package oxy.bascenario.serializers.types.event.impl.element;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.netty.buffer.ByteBuf;
import oxy.bascenario.api.event.element.ElementIndexEvent;
import oxy.bascenario.serializers.Types;
import oxy.bascenario.serializers.base.TypeWithName;

public class ElementIndexType implements TypeWithName<ElementIndexEvent> {
    @Override
    public String type() {
        return "element-index";
    }

    @Override
    public JsonElement write(ElementIndexEvent elementIndexEvent) {
        final JsonObject object = new JsonObject();
        object.addProperty("id", elementIndexEvent.mainIndex());
        object.add("subId", Types.NULLABLE_INT.write(elementIndexEvent.subIndex()));
        object.addProperty("newIndex", elementIndexEvent.newIndex());
        object.addProperty("swap", elementIndexEvent.swap());
        return object;
    }

    @Override
    public ElementIndexEvent read(JsonElement element) {
        final JsonObject object = element.getAsJsonObject();
        return new ElementIndexEvent(object.get("id").getAsInt(), Types.NULLABLE_INT.read(object.get("subId")), object.get("newIndex").getAsInt(), object.get("swap").getAsBoolean());
    }

    @Override
    public void write(ElementIndexEvent elementIndexEvent, ByteBuf buf) {
        buf.writeInt(elementIndexEvent.mainIndex());
        Types.NULLABLE_INT.write(elementIndexEvent.subIndex(), buf);
        buf.writeInt(elementIndexEvent.newIndex());
        buf.writeBoolean(elementIndexEvent.swap());
    }

    @Override
    public ElementIndexEvent read(ByteBuf buf) {
        return new ElementIndexEvent(buf.readInt(), Types.NULLABLE_INT.read(buf), buf.readInt(), buf.readBoolean());
    }
}

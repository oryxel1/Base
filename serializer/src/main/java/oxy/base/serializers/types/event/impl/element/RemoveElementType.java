package oxy.base.serializers.types.event.impl.element;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import oxy.base.api.event.element.RemoveElementEvent;
import oxy.base.serializers.Types;
import oxy.base.serializers.base.TypeWithName;

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
        final JsonObject object = element.getAsJsonObject();
        return new RemoveElementEvent(object.get("id").getAsInt(), Types.NULLABLE_INT.read(object.get("subId")));
    }
}

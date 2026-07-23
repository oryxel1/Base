package oxy.base.serializers.types.event.impl.color;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import oxy.base.api.event.color.SetColorEvent;
import oxy.base.serializers.Types;
import oxy.base.serializers.base.TypeWithName;

public class SetColorType implements TypeWithName<SetColorEvent> {
    @Override
    public String type() {
        return "set-color";
    }

    @Override
    public JsonElement write(SetColorEvent event) {
        final JsonObject object = new JsonObject();
        object.addProperty("id", event.id());
        object.addProperty("duration", event.duration());
        object.add("color", Types.COLOR_TYPE.write(event.color()));
        return object;
    }

    @Override
    public SetColorEvent read(JsonElement element) {
        final JsonObject object = element.getAsJsonObject();
        return new SetColorEvent(object.get("id").getAsInt(), object.get("duration").getAsInt(), Types.COLOR_TYPE.read(object.get("color")));
    }
}

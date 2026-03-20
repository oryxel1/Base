package oxy.bascenario.serializers.types.event.impl.element;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import oxy.bascenario.api.event.element.AddElementEvent;
import oxy.bascenario.serializers.Types;
import oxy.bascenario.serializers.base.TypeWithName;

public class AddElementType implements TypeWithName<AddElementEvent> {
    @Override
    public String type() {
        return "add-element";
    }

    @Override
    public JsonElement write(AddElementEvent addElementEvent) {
        final JsonObject object = new JsonObject();
        object.addProperty("id", addElementEvent.id());
        object.add("position", Types.VECTOR_2F_TYPE.write(addElementEvent.position()));
        object.add("layer", Types.RENDER_LAYER_TYPE.write(addElementEvent.layer()));
        object.add("object", Types.ELEMENT_TYPE.write(addElementEvent.element()));
        return object;
    }

    @Override
    public AddElementEvent read(JsonElement element) {
        final JsonObject object = element.getAsJsonObject();
        return new AddElementEvent(object.get("id").getAsInt(), Types.VECTOR_2F_TYPE.read(object.get("position")), Types.ELEMENT_TYPE.read(object.get("object")), Types.RENDER_LAYER_TYPE.read(object.get("layer")));
    }
}

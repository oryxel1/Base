package oxy.bascenario.serializers.types.element.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import oxy.bascenario.api.render.elements.Preview;
import oxy.bascenario.serializers.Types;
import oxy.bascenario.serializers.base.TypeWithName;
import oxy.bascenario.serializers.types.element.ElementTypes;

public class PreviewType implements TypeWithName<Preview> {
    @Override
    public String type() {
        return "preview";
    }

    @Override
    public JsonElement write(Preview preview) {
        final JsonObject object = new JsonObject();
        object.add("font", ElementTypes.FONT_TYPE_TYPE.write(preview.type()));
        object.addProperty("title", preview.title());
        object.addProperty("subtitle", preview.subtitle());
        object.add("background", Types.NULLABLE_FILE_INFO_TYPE.write(preview.background()));
        return object;
    }

    @Override
    public Preview read(JsonElement element) {
        final JsonObject object = element.getAsJsonObject();
        return new Preview(ElementTypes.FONT_TYPE_TYPE.read(object.get("font")), object.get("title").getAsString(), object.get("subtitle").getAsString(), Types.NULLABLE_FILE_INFO_TYPE.read(object.get("background")));
    }
}

package oxy.base.serializers.types.event.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import oxy.base.api.event.PopupEvent;
import oxy.base.serializers.Types;
import oxy.base.serializers.base.TypeWithName;
import oxy.base.serializers.types.primitive.EnumType;

public class PopupType implements TypeWithName<PopupEvent> {
    private final EnumType<PopupEvent.Type> type = new EnumType<>(PopupEvent.Type.class, PopupEvent.Type.values());

    @Override
    public JsonElement write(PopupEvent popupEvent) {
        final JsonObject object = new JsonObject();
        object.add("type", type.write(popupEvent.type()));
        object.add("file", Types.NULLABLE_FILE_INFO_TYPE.write(popupEvent.popup()));
        return object;
    }

    @Override
    public PopupEvent read(JsonElement element) {
        final JsonObject object = element.getAsJsonObject();
        return new PopupEvent(type.read(object.get("type")), Types.NULLABLE_FILE_INFO_TYPE.read(object.get("file")));
    }

    @Override
    public String type() {
        return "popup-event";
    }
}

package oxy.bascenario.serializers.types.element.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.netty.buffer.ByteBuf;
import oxy.bascenario.api.render.elements.LocationInfo;
import oxy.bascenario.serializers.Types;
import oxy.bascenario.serializers.base.TypeWithName;
import oxy.bascenario.serializers.types.element.ElementTypes;

public class LocationInfoType implements TypeWithName<LocationInfo> {
    @Override
    public JsonElement write(LocationInfo locationInfo) {
        final JsonObject object = new JsonObject();
        object.add("font", ElementTypes.FONT_TYPE_TYPE.write(locationInfo.font()));
        object.addProperty("location", locationInfo.location());
        object.addProperty("duration", locationInfo.duration());
        object.addProperty("fade", locationInfo.fade());
        return object;
    }

    @Override
    public LocationInfo read(JsonElement element) {
        final JsonObject object = element.getAsJsonObject();
        return new LocationInfo(ElementTypes.FONT_TYPE_TYPE.read(object.get("font")), object.get("location").getAsString(), object.get("duration").getAsInt(), object.get("fade").getAsInt());
    }

    @Override
    public void write(LocationInfo locationInfo, ByteBuf buf) {
        ElementTypes.FONT_TYPE_TYPE.write(locationInfo.font(), buf);
        Types.STRING_TYPE.write(locationInfo.location(), buf);
        buf.writeInt(locationInfo.duration());
        buf.writeInt(locationInfo.fade());
    }

    @Override
    public LocationInfo read(ByteBuf buf) {
        return new LocationInfo(ElementTypes.FONT_TYPE_TYPE.read(buf), Types.STRING_TYPE.read(buf), buf.readInt(), buf.readInt());
    }

    @Override
    public String type() {
        return "location-info";
    }
}

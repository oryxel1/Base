package oxy.bascenario.serializers.types.element.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.netty.buffer.ByteBuf;
import oxy.bascenario.api.render.elements.LocationInfo;
import oxy.bascenario.serializers.Types;
import oxy.bascenario.serializers.base.TypeWithName;

public class LocationInfoType implements TypeWithName<LocationInfo> {
    @Override
    public JsonElement write(LocationInfo locationInfo) {
        final JsonObject object = new JsonObject();
        object.addProperty("location", locationInfo.location());
        object.addProperty("duration", locationInfo.duration());
        object.addProperty("fade", locationInfo.fade());
        return object;
    }

    @Override
    public LocationInfo read(JsonElement element) {
        final JsonObject object = element.getAsJsonObject();
        return new LocationInfo(object.get("location").getAsString(), object.get("duration").getAsInt(), object.get("fade").getAsInt());
    }

    @Override
    public void write(LocationInfo locationInfo, ByteBuf buf) {
        Types.STRING_TYPE.write(locationInfo.location());
        buf.writeInt(locationInfo.duration());
        buf.writeInt(locationInfo.fade());
    }

    @Override
    public LocationInfo read(ByteBuf buf) {
        return new LocationInfo(Types.STRING_TYPE.read(buf), buf.readInt(), buf.readInt());
    }

    @Override
    public String type() {
        return "location-info";
    }
}

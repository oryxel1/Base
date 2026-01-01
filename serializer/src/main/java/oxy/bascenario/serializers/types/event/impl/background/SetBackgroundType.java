package oxy.bascenario.serializers.types.event.impl.background;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.netty.buffer.ByteBuf;
import oxy.bascenario.api.event.background.SetBackgroundEvent;
import oxy.bascenario.serializers.Types;
import oxy.bascenario.serializers.base.TypeWithName;

public class SetBackgroundType implements TypeWithName<SetBackgroundEvent> {
    @Override
    public String type() {
        return "set-background";
    }

    @Override
    public JsonElement write(SetBackgroundEvent setBackgroundEvent) {
        final JsonObject object = new JsonObject();
        object.add("background", Types.FILE_INFO_TYPE.write(setBackgroundEvent.background()));
        object.addProperty("duration", setBackgroundEvent.duration());
        return object;
    }

    @Override
    public SetBackgroundEvent read(JsonElement element) {
        final JsonObject object = element.getAsJsonObject();
        return new SetBackgroundEvent(Types.FILE_INFO_TYPE.read(object.get("background")), object.get("duration").getAsInt());
    }

    @Override
    public void write(SetBackgroundEvent setBackgroundEvent, ByteBuf buf) {
        Types.FILE_INFO_TYPE.write(setBackgroundEvent.background(), buf);
        buf.writeInt(setBackgroundEvent.duration());
    }

    @Override
    public SetBackgroundEvent read(ByteBuf buf) {
        return new SetBackgroundEvent(Types.FILE_INFO_TYPE.read(buf), buf.readInt());
    }
}

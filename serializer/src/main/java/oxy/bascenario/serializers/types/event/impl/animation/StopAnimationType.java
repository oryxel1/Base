package oxy.bascenario.serializers.types.event.impl.animation;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.netty.buffer.ByteBuf;
import oxy.bascenario.api.event.animation.StopAnimationEvent;
import oxy.bascenario.serializers.Types;
import oxy.bascenario.serializers.base.TypeWithName;

public class StopAnimationType implements TypeWithName<StopAnimationEvent> {
    @Override
    public String type() {
        return "stop-animation";
    }

    @Override
    public JsonElement write(StopAnimationEvent stopAnimationEvent) {
        final JsonObject object = new JsonObject();
        object.addProperty("id", stopAnimationEvent.id());
        object.addProperty("name", stopAnimationEvent.name());
        return object;
    }

    @Override
    public StopAnimationEvent read(JsonElement element) {
        final JsonObject object = element.getAsJsonObject();
        return new StopAnimationEvent(object.get("id").getAsInt(), object.get("name").getAsString());
    }

    @Override
    public void write(StopAnimationEvent stopAnimationEvent, ByteBuf buf) {
        buf.writeInt(stopAnimationEvent.id());
        Types.STRING_TYPE.write(stopAnimationEvent.name());
    }

    @Override
    public StopAnimationEvent read(ByteBuf buf) {
        return new StopAnimationEvent(buf.readInt(), Types.STRING_TYPE.read(buf));
    }
}

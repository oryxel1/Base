package oxy.bascenario.serializers.types.event.impl.animation;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.netty.buffer.ByteBuf;
import oxy.bascenario.api.event.animation.SpriteAnimationEvent;
import oxy.bascenario.serializers.Types;
import oxy.bascenario.serializers.base.TypeWithName;

public class SpriteAnimationType implements TypeWithName<SpriteAnimationEvent> {
    @Override
    public String type() {
        return "sprite-animation";
    }

    @Override
    public JsonElement write(SpriteAnimationEvent stopAnimationEvent) {
        final JsonObject object = new JsonObject();
        object.addProperty("id", stopAnimationEvent.id());
        object.addProperty("mixTime", stopAnimationEvent.mixTime());
        object.addProperty("name", stopAnimationEvent.animationName());
        object.addProperty("trackIndex", stopAnimationEvent.trackIndex());
        object.addProperty("loop", stopAnimationEvent.loop());
        return object;
    }

    @Override
    public SpriteAnimationEvent read(JsonElement element) {
        final JsonObject object = element.getAsJsonObject();
        return new SpriteAnimationEvent(object.get("id").getAsInt(),
                object.get("mixTime").getAsFloat(), object.get("name").getAsString(), object.get("trackIndex").getAsInt(), object.get("loop").getAsBoolean());
    }

    @Override
    public void write(SpriteAnimationEvent stopAnimationEvent, ByteBuf buf) {
        buf.writeInt(stopAnimationEvent.id());
        buf.writeFloat(stopAnimationEvent.mixTime());
        Types.STRING_TYPE.write(stopAnimationEvent.animationName(), buf);
        buf.writeInt(stopAnimationEvent.trackIndex());
        buf.writeBoolean(stopAnimationEvent.loop());
    }

    @Override
    public SpriteAnimationEvent read(ByteBuf buf) {
        return new SpriteAnimationEvent(buf.readInt(), buf.readFloat(), Types.STRING_TYPE.read(buf), buf.readInt(), buf.readBoolean());
    }
}

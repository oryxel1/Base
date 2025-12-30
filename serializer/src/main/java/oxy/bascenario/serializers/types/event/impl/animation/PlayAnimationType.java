package oxy.bascenario.serializers.types.event.impl.animation;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.netty.buffer.ByteBuf;
import oxy.bascenario.api.event.animation.PlayAnimationEvent;
import oxy.bascenario.serializers.Types;
import oxy.bascenario.serializers.base.TypeWithName;

public class PlayAnimationType implements TypeWithName<PlayAnimationEvent> {
    @Override
    public String type() {
        return "play-animation";
    }

    @Override
    public JsonElement write(PlayAnimationEvent stopAnimationEvent) {
        final JsonObject object = new JsonObject();
        object.addProperty("id", stopAnimationEvent.id());
        object.addProperty("name", stopAnimationEvent.name());
        object.addProperty("loop", stopAnimationEvent.loop());
        return object;
    }

    @Override
    public PlayAnimationEvent read(JsonElement element) {
        final JsonObject object = element.getAsJsonObject();
        return new PlayAnimationEvent(object.get("id").getAsInt(), object.get("name").getAsString(), object.get("loop").getAsBoolean());
    }

    @Override
    public void write(PlayAnimationEvent stopAnimationEvent, ByteBuf buf) {
        buf.writeInt(stopAnimationEvent.id());
        Types.STRING_TYPE.write(stopAnimationEvent.name(), buf);
        buf.writeBoolean(stopAnimationEvent.loop());
    }

    @Override
    public PlayAnimationEvent read(ByteBuf buf) {
        return new PlayAnimationEvent(buf.readInt(), Types.STRING_TYPE.read(buf), buf.readBoolean());
    }
}

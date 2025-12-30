package oxy.bascenario.serializers.types.event.impl.sound;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.netty.buffer.ByteBuf;
import oxy.bascenario.api.event.sound.SoundEvent;
import oxy.bascenario.serializers.base.TypeWithName;

public class SoundEventType implements TypeWithName<SoundEvent> {
    @Override
    public String type() {
        return "sound-event";
    }

    @Override
    public JsonElement write(SoundEvent soundEvent) {
        final JsonObject object = new JsonObject();
        object.addProperty("id", soundEvent.id());
        object.addProperty("event", soundEvent.event().name());
        return object;
    }

    @Override
    public SoundEvent read(JsonElement element) {
        final JsonObject object = element.getAsJsonObject();
        return new SoundEvent(object.get("id").getAsInt(), SoundEvent.Event.valueOf(object.get("event").getAsString()));
    }

    @Override
    public void write(SoundEvent soundEvent, ByteBuf buf) {
        buf.writeInt(soundEvent.id());
        buf.writeByte(soundEvent.event().ordinal());
    }

    @Override
    public SoundEvent read(ByteBuf buf) {
        return new SoundEvent(buf.readInt(), SoundEvent.Event.values()[buf.readByte()]);
    }
}

package oxy.bascenario.serializers.types.event.impl.sound;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.netty.buffer.ByteBuf;
import oxy.bascenario.api.event.sound.SoundVolumeEvent;
import oxy.bascenario.serializers.Types;
import oxy.bascenario.serializers.base.TypeWithName;

public class SoundVolumeType implements TypeWithName<SoundVolumeEvent> {
    @Override
    public String type() {
        return "sound-volume";
    }

    @Override
    public JsonElement write(SoundVolumeEvent soundVolumeEvent) {
        final JsonObject object = new JsonObject();
        object.addProperty("id", soundVolumeEvent.id());
        object.addProperty("duration", soundVolumeEvent.duration());
        object.addProperty("volume", soundVolumeEvent.volume());
        object.add("easing", Types.EASING_TYPE.write(soundVolumeEvent.easing()));
        return object;
    }

    @Override
    public SoundVolumeEvent read(JsonElement element) {
        final JsonObject object = element.getAsJsonObject();
        return new SoundVolumeEvent(object.get("id").getAsInt(), object.get("duration").getAsInt(), object.get("volume").getAsFloat(), Types.EASING_TYPE.read(object.get("easing")));
    }

    @Override
    public void write(SoundVolumeEvent soundVolumeEvent, ByteBuf buf) {
        buf.writeInt(soundVolumeEvent.id());
        buf.writeInt(soundVolumeEvent.duration());
        buf.writeFloat(soundVolumeEvent.volume());
        Types.EASING_TYPE.write(soundVolumeEvent.easing(), buf);
    }

    @Override
    public SoundVolumeEvent read(ByteBuf buf) {
        return new SoundVolumeEvent(buf.readInt(), buf.readInt(), buf.readFloat(), Types.EASING_TYPE.read(buf));
    }
}

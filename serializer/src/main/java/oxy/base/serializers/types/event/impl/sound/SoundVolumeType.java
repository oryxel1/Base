package oxy.base.serializers.types.event.impl.sound;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import oxy.base.api.event.sound.SoundVolumeEvent;
import oxy.base.serializers.Types;
import oxy.base.serializers.base.TypeWithName;

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
}

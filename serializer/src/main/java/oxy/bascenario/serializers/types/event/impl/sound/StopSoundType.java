package oxy.bascenario.serializers.types.event.impl.sound;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import oxy.bascenario.api.event.sound.StopSoundEvent;
import oxy.bascenario.serializers.base.TypeWithName;

public class StopSoundType implements TypeWithName<StopSoundEvent> {
    @Override
    public String type() {
        return "stop-sound";
    }

    @Override
    public JsonElement write(StopSoundEvent event) {
        final JsonObject object = new JsonObject();
        object.addProperty("id", event.id());
        object.addProperty("duration", event.duration());
        return object;
    }

    @Override
    public StopSoundEvent read(JsonElement element) {
        final JsonObject object = element.getAsJsonObject();
        return new StopSoundEvent(object.get("id").getAsInt(), object.get("duration").getAsInt());
    }
}

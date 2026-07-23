package oxy.base.serializers.types.event.impl.animation;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import oxy.base.api.event.animation.StopAnimationEvent;
import oxy.base.serializers.base.TypeWithName;

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
}

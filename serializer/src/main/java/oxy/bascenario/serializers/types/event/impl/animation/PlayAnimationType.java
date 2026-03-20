package oxy.bascenario.serializers.types.event.impl.animation;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

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
}

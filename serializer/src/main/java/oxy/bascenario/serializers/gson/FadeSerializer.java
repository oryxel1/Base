package oxy.bascenario.serializers.gson;

import com.google.gson.*;
import oxy.bascenario.api.effects.Fade;

import java.lang.reflect.Type;

public class FadeSerializer implements JsonSerializer<Fade>, JsonDeserializer<Fade> {
    @Override
    public Fade deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        int duration = json.getAsJsonPrimitive().getAsInt();
        return duration <= 0 ? Fade.DISABLED : new Fade(duration);
    }

    @Override
    public JsonElement serialize(Fade src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.duration());
    }
}

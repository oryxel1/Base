package oxy.bascenario.serializers.utils;

import com.google.gson.*;
import lombok.Getter;

public class GsonUtils {
    @Getter
    private static final Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting()
//            .registerTypeAdapter(Event.class, new EventSerializer())
            .create();

    @SuppressWarnings("ALL")
    public static JsonObject toJson(final Object object) {
        TypeAdapter adapter = gson.getAdapter(object.getClass());
        return JsonParser.parseString((adapter == null ? gson.toJson(object) : adapter.toJson(object)).trim()).getAsJsonObject();
    }
}

package oxy.bascenario.serializers.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import oxy.bascenario.api.effects.Fade;
import oxy.bascenario.api.event.Event;
import oxy.bascenario.api.utils.FileInfo;
import oxy.bascenario.serializers.FadeSerializer;
import oxy.bascenario.serializers.FileInfoSerializer;

public class GsonUtils {
    @Getter
    private static final Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting()
//            .registerTypeAdapter(Event.class, new EventSerializer())
            .registerTypeAdapter(FileInfo.class, new FileInfoSerializer())
            .registerTypeAdapter(Fade.class, new FadeSerializer())
            .create();

    public static JsonObject toJson(final Object object) {
        return JsonParser.parseString(gson.toJson(object).trim()).getAsJsonObject();
    }

    public static JsonObject toJson(final Event<?> event) {
        return JsonParser.parseString(gson.getAdapter(Event.class).toJson(event).trim()).getAsJsonObject();
    }
}

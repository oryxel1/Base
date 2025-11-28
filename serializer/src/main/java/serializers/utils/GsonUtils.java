package serializers.utils;

import com.google.gson.*;
import lombok.Getter;
import oxy.bascenario.api.effects.Fade;
import oxy.bascenario.api.utils.FileInfo;
import serializers.FadeSerializer;
import serializers.FileInfoSerializer;

public class GsonUtils {
    @Getter
    private static final Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting()
//            .registerTypeAdapter(Event.class, new EventSerializer())
            .registerTypeAdapter(FileInfo.class, new FileInfoSerializer())
            .registerTypeAdapter(Fade.class, new FadeSerializer())
            .create();

    @SuppressWarnings("ALL")
    public static JsonObject toJson(final Object object) {
        TypeAdapter adapter = gson.getAdapter(object.getClass());
        return JsonParser.parseString((adapter == null ? gson.toJson(object) : adapter.toJson(object)).trim()).getAsJsonObject();
    }
}

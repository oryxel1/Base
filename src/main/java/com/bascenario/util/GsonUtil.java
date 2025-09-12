package com.bascenario.util;

import com.bascenario.engine.scenario.event.EventSerializer;
import com.bascenario.engine.scenario.event.api.Event;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;

public final class GsonUtil {
    @Getter
    private static final Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting()
            .registerTypeAdapter(Event.class, new EventSerializer())
            .create();

    public static JsonObject toJson(final Object object) {
        return JsonParser.parseString(gson.toJson(object).trim()).getAsJsonObject();
    }

    public static JsonObject toJson(final Event<?> event) {
        return JsonParser.parseString(gson.getAdapter(Event.class).toJson(event).trim()).getAsJsonObject();
    }
}

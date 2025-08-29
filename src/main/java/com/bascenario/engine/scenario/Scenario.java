package com.bascenario.engine.scenario;

import com.bascenario.engine.scenario.elements.Background;
import com.bascenario.engine.scenario.elements.Sound;
import com.bascenario.engine.scenario.event.EventSerializer;
import com.bascenario.engine.scenario.event.api.Event;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor
@Getter
@Builder
public class Scenario {
    private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting()
            .registerTypeAdapter(Event.class, new EventSerializer())
            .create();
    public static Scenario fromJson(String json) {
        return GSON.fromJson(json.trim(), Scenario.class);
    }

    private final String name;

    private final Background previewBackground;
    private final Sound previewSound;

    @Getter
    private final List<Timestamp> timestamps = new ArrayList<>();

    public void add(long time, Event... events) {
        this.add(false, time, events);
    }

    public void add(boolean waitForDialogue, long time, Event... events) {
        this.timestamps.add(new Timestamp(waitForDialogue, time, List.of(events)));
    }

    @Builder
    public record Timestamp(boolean waitForDialogue, long time, List<Event> events) {
    }

    public JsonObject toJson() {
        return JsonParser.parseString(GSON.toJson(this)).getAsJsonObject();
    }
}

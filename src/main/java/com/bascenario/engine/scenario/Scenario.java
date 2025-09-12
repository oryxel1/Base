package com.bascenario.engine.scenario;

import com.bascenario.engine.scenario.elements.Background;
import com.bascenario.engine.scenario.elements.Sound;
import com.bascenario.engine.scenario.event.api.Event;
import com.bascenario.util.GsonUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor
@Getter
@Builder
public class Scenario {
    public static Scenario fromJson(String json) {
        return GsonUtil.getGson().fromJson(json.trim(), Scenario.class);
    }

    private final String name;

    @SerializedName("preview-background")
    private final Background previewBackground;
    @SerializedName("preview-sound")
    private final Sound previewSound;

    @Getter
    private final List<Timestamp> timestamps = new ArrayList<>();

    public void add(long time, Event<?>... events) {
        this.add(false, time, events);
    }

    public void add(boolean waitForDialogue, long time, Event<?>... events) {
        this.timestamps.add(new Timestamp(waitForDialogue, time, List.of(events)));
    }

    @Builder
    public record Timestamp(@SerializedName("wait-for-dialogue") boolean waitForDialogue, long time, List<Event<?>> events) {
    }

    public JsonObject toJson() {
        return JsonParser.parseString(GsonUtil.getGson().toJson(this)).getAsJsonObject();
    }
}

package com.bascenario.engine.scenario;

import com.bascenario.engine.scenario.elements.Background;
import com.bascenario.engine.scenario.elements.Sound;
import com.bascenario.engine.scenario.event.api.Event;
import com.bascenario.util.GsonUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor
@Getter
public class Scenario {
    public static Scenario fromJson(String json) {
        return GsonUtil.getGson().fromJson(json.trim(), Scenario.class);
    }

    public JsonObject toJson() {
        return JsonParser.parseString(GsonUtil.getGson().toJson(this)).getAsJsonObject();
    }

    private final String name;

    @SerializedName("preview-background")
    private final Background previewBackground;
    @SerializedName("preview-sound")
    private final Sound previewSound;

    @Getter
    private final List<Timestamp> timestamps = new ArrayList<>();

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(Scenario scenario) {
        final Builder builder = new Builder();
        builder.previewBackground = scenario.previewBackground;
        builder.previewSound = scenario.previewSound;
        builder.name = scenario.name;
        builder.timestamps.addAll(scenario.timestamps);

        return builder;
    }

    public static class Builder {
        private String name = "";
        private Background previewBackground;
        private Sound previewSound;
        private final List<Timestamp> timestamps = new ArrayList<>();

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public String name() {
            return this.name;
        }

        public Builder previewBackground(Background previewBackground) {
            this.previewBackground = previewBackground;
            return this;
        }

        public Background previewBackground() {
            return this.previewBackground;
        }

        public Sound previewSound() {
            return previewSound;
        }

        public Builder previewSound(Sound previewSound) {
            this.previewSound = previewSound;
            return this;
        }

        public Builder add(long time, Event<?>... events) {
            this.add(false, time, events);
            return this;
        }

        public Builder add(boolean waitForDialogue, long time, Event<?>... events) {
            this.timestamps.add(new Timestamp(waitForDialogue, time, List.of(events)));
            return this;
        }

        public List<Timestamp> timestamps() {
            return this.timestamps;
        }

        public Scenario build() {
            final Scenario scenario = new Scenario(this.name, this.previewBackground, this.previewSound);
            scenario.timestamps.addAll(this.timestamps);
            return scenario;
        }
    }
}

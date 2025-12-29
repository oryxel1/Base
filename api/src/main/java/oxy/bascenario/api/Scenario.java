package oxy.bascenario.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import oxy.bascenario.api.event.api.Event;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
@RequiredArgsConstructor
@Getter
public class Scenario {
    private final String name;
    private final SaveType saveType;

    private final List<Timestamp> timestamps = new ArrayList<>();

    public static Builder builder() {
        return new Builder();
    }

    public static Builder toBuilder(Scenario scenario) {
        final Builder builder = new Builder();
        builder.saveType = scenario.saveType;
        builder.timestamps.addAll(scenario.timestamps);

        return builder;
    }

    public static final class Builder {
        private String name;
        private SaveType saveType = SaveType.JSON;
        private final List<Timestamp> timestamps = new ArrayList<>();

        public String name() {
            return name;
        }

        public void name(String name) {
            this.name = name;
        }

        public SaveType saveType() {
            return this.saveType;
        }

        public void saveType(SaveType saveType) {
            this.saveType = saveType;
        }

        public Builder add(long time, Event... events) {
            this.add(false, time, events);
            return this;
        }

        public Builder add(boolean waitForDialogue, long time, Event... events) {
            this.timestamps.add(new Timestamp(waitForDialogue, time, new ArrayList<>(List.of(events))));
            return this;
        }

        public List<Timestamp> timestamps() {
            return this.timestamps;
        }

        public Scenario build() {
            final Scenario scenario = new Scenario(this.name, this.saveType);
            scenario.timestamps.addAll(this.timestamps);
            return scenario;
        }
    }

    public enum SaveType {
        BINARY, JSON
    }
}

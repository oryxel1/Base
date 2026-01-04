package oxy.bascenario.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import oxy.bascenario.api.event.api.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("ALL")
@RequiredArgsConstructor
@Getter
public class Scenario {
    private final String name;
    private final SaveType saveType;

    private final List<Timestamp> timestamps;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Scenario scenario = (Scenario) o;
        if (!Objects.equals(name, scenario.name) || saveType != scenario.saveType || timestamps.size() != scenario.getTimestamps().size()) {
            return false;
        }
        int i = 0;
        for (final Timestamp timestamp : scenario.getTimestamps()) {
            if (!timestamps.get(i).equals(timestamp)) {
                return false;
            }
            i++;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, saveType, timestamps);
    }

    public static Builder builder() {
        return new Builder(new ArrayList<>());
    }

    public Builder toBuilder() {
        final Builder builder = new Builder(timestamps);
        builder.saveType = saveType;
        builder.name = name;
        return builder;
    }

    @RequiredArgsConstructor
    public static final class Builder {
        private String name = "";
        private SaveType saveType = SaveType.JSON;
        private final List<Timestamp> timestamps;

        public Builder() {
            this.timestamps = new ArrayList<>();
        }

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

        public Builder add(int time, Event... events) {
            this.add(false, time, events);
            return this;
        }

        public Builder add(boolean waitForDialogue, int time, Event... events) {
            this.timestamps.add(new Timestamp(waitForDialogue, time, new ArrayList<>(List.of(events))));
            return this;
        }

        public List<Timestamp> timestamps() {
            return this.timestamps;
        }

        public Scenario build() {
            return new Scenario(this.name, this.saveType, this.timestamps);
        }
    }

    public enum SaveType {
        BINARY, JSON
    }
}

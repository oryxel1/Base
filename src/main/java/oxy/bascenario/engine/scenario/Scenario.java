package oxy.bascenario.engine.scenario;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import oxy.bascenario.engine.base.Image;
import oxy.bascenario.engine.base.Sound;
import oxy.bascenario.engine.scenario.event.base.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@RequiredArgsConstructor
@Getter
public final class Scenario {
    private final String name;
    private final String subtitle;

    private final Optional<Sound> previewSound;
    private final Optional<Image> previewBackground;

    private final List<String> downloads = new ArrayList<>();
    private final List<Timestamp> timestamps = new ArrayList<>();

    public static Builder builder() {
        return new Builder();
    }

    public static Builder toBuilder(Scenario scenario) {
        final Builder builder = new Builder();
        builder.previewBackground = scenario.previewBackground;
        builder.previewSound = scenario.previewSound;
        builder.name = scenario.name;
        builder.timestamps.addAll(scenario.timestamps);

        return builder;
    }

    public static class Builder {
        private String name = "", subtitle = "";
        private Optional<Image> previewBackground;
        private Optional<Sound> previewSound;
        private final List<Timestamp> timestamps = new ArrayList<>();
        private final List<String> downloads = new ArrayList<>();

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public String name() {
            return this.name;
        }

        public Builder subtitle(String subtitle) {
            this.subtitle = subtitle;
            return this;
        }

        public String subtitle() {
            return this.subtitle;
        }

        public Builder previewBackground(Image previewBackground) {
            this.previewBackground = Optional.of(previewBackground);
            return this;
        }

        public Optional<Image> previewBackground() {
            return this.previewBackground;
        }

        public Optional<Sound> previewSound() {
            return this.previewSound;
        }

        public Builder previewSound(Sound previewSound) {
            this.previewSound = Optional.of(previewSound);
            return this;
        }

        public Builder add(long time, Event<?>... events) {
            this.add(false, time, events);
            return this;
        }

        public Builder add(boolean waitForDialogue, long time, Event<?>... events) {
            final List<Event<?>> eventsAsList = List.of(events);
            eventsAsList.forEach(event -> this.downloads.addAll(event.downloads()));

            this.timestamps.add(new Timestamp(waitForDialogue, time, new ArrayList<>(eventsAsList)));
            return this;
        }

        public List<Timestamp> timestamps() {
            return this.timestamps;
        }

        public Scenario build() {
            final Scenario scenario = new Scenario(this.name, this.subtitle, this.previewSound, this.previewBackground);
            scenario.timestamps.addAll(this.timestamps);

            if (this.previewBackground.isPresent() && this.previewBackground.get().file().url().isPresent()) {
                scenario.downloads.add(this.previewBackground.get().file().url().get());
            }

            if (this.previewSound.isPresent() && this.previewSound.get().file().url().isPresent()) {
                scenario.downloads.add(this.previewSound.get().file().url().get());
            }

            scenario.downloads.addAll(this.downloads);
            return scenario;
        }
    }
}

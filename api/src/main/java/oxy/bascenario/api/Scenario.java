package oxy.bascenario.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import oxy.bascenario.api.effects.Sound;
import oxy.bascenario.api.elements.Image;
import oxy.bascenario.api.event.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@RequiredArgsConstructor
@Getter
public class Scenario {
    private final String title, subtitle;

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
        builder.title = scenario.title;
        builder.timestamps.addAll(scenario.timestamps);

        return builder;
    }

    public static class Builder {
        private String title = "", subtitle = "";
        private Optional<Image> previewBackground;
        private Optional<Sound> previewSound;
        private final List<Timestamp> timestamps = new ArrayList<>();
        private final List<String> downloads = new ArrayList<>();

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public String title() {
            return this.title;
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
            final Scenario scenario = new Scenario(this.title, this.subtitle, this.previewSound, this.previewBackground);
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

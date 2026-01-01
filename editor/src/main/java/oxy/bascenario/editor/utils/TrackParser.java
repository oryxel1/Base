package oxy.bascenario.editor.utils;

import oxy.bascenario.api.Scenario;
import oxy.bascenario.api.Timestamp;
import oxy.bascenario.api.event.api.Event;
import oxy.bascenario.api.event.element.AddElementEvent;
import oxy.bascenario.api.event.element.AttachElementEvent;
import oxy.bascenario.api.event.element.RemoveElementEvent;
import oxy.bascenario.api.event.sound.PlaySoundEvent;
import oxy.bascenario.api.event.sound.StopSoundEvent;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.editor.element.Timeline;
import oxy.bascenario.editor.element.Track;
import oxy.bascenario.utils.Pair;
import oxy.bascenario.api.effects.Sound;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class TrackParser {
    // TODO: Sub elements....
    public static List<Timestamp> parse(Map<Integer, Track> tracks) {
        final List<Timestamp> timestamps = new ArrayList<>();

        final Map<Long, Pair<Boolean, List<Event>>> events = new TreeMap<>();
        for (Map.Entry<Integer, Track> entry : tracks.entrySet()) {
            Integer i = entry.getKey(); Track track = entry.getValue();
            track.getObjects().forEach((l, object) -> {
                final Pair<Boolean, List<Event>> list = events.computeIfAbsent(l, n -> new Pair<>(object.requireWait, new ArrayList<>()));
                if (object.object instanceof Event event) {
                    list.right().add(event);
                } else if (object.object instanceof SoundAsElement(Sound sound, int in, int out, float start, long max)) {
                    list.right().add(new PlaySoundEvent(sound, in, start));
                    if (out != Integer.MIN_VALUE && sound != null) {
                        events.computeIfAbsent(l + object.duration, n -> new Pair<>(object.requireWait, new ArrayList<>())).right().add(new StopSoundEvent(sound.id(), out));
                    }
                } else {
                    list.right().add(new AddElementEvent(i, object.object, object.layer));
                    events.computeIfAbsent(l + object.duration, n -> new Pair<>(object.requireWait, new ArrayList<>())).right().add(new RemoveElementEvent(i));
                }
            });
        }

        long last = 0;
        for (Map.Entry<Long, Pair<Boolean, List<Event>>> entry : events.entrySet()) {
            final Long time = entry.getKey();
            long delay = time - last;
            last = time;

            timestamps.add(new Timestamp(entry.getValue().left(), (int) delay, entry.getValue().right()));
        }

        return timestamps;
    }

    public static Map<Integer, Track> parse(Timeline timeline, Scenario scenario) {
        final Map<Integer, Track> result = new ConcurrentHashMap<>();

        long time = 0;
        for (Timestamp timestamp : scenario.getTimestamps()) {
            time += timestamp.time();

            for (Event other : timestamp.events()) { switch (other) {
                case AddElementEvent event -> {
                    Track track = result.computeIfAbsent(event.id(), id -> new Track(timeline, id));
                    long maxTime = TimeCompiler.compileTime(event.element());
                    track.put(time, maxTime, event.element(), event.layer(), timestamp.waitForDialogue());
                    track.prevObjectStart = time;
                }
                case RemoveElementEvent event -> {
                    Track track = result.get(event.id());
                    if (track == null) {
                        continue;
                    }

                    final Track.ObjectOrEvent objectOrEvent = track.getObjects().get(track.prevObjectStart);
                    if (objectOrEvent == null) {
                        continue;
                    }

                    objectOrEvent.duration = Math.min(time - track.prevObjectStart, objectOrEvent.duration);
                }
                case PlaySoundEvent event -> {
                    long duration = AudioUtils.toDuration(scenario.getName(), event.sound().file());
                    Track track = findNonOccupiedSlot(timeline, time, duration, result);

                    final SoundAsElement element = new SoundAsElement(event.sound(), (int) event.duration(), 0, event.start(), duration);
                    track.put(time, duration, element, null, timestamp.waitForDialogue());
                }
                case StopSoundEvent event -> {
                    Track track = result.get(event.id());
                    if (track == null) {
                        continue;
                    }

                    final Track.ObjectOrEvent objectOrEvent = track.getObjects().get(track.prevObjectStart);
                    if (objectOrEvent == null || !(objectOrEvent.object instanceof SoundAsElement sound)) {
                        continue;
                    }

                    final SoundAsElement.Builder builder = sound.toBuilder();
                    builder.out(event.duration());

                    objectOrEvent.object = builder.build();
                    objectOrEvent.duration = Math.min(time - track.prevObjectStart, objectOrEvent.duration);
                }
                default -> {
                    long duration = TimeCompiler.compileTime(other);
                    Track track = findNonOccupiedSlot(timeline, time, duration, result);

                    track.put(time, duration, other, null, timestamp.waitForDialogue());
                }
            }}
        }

        return result;
    }

    private static Track findNonOccupiedSlot(Timeline timeline, long time, long duration, Map<Integer, Track> tracks) {
        int i = 0;
        Track track;
        while ((track = tracks.get(i)) != null) {
            if (track.isNotOccupied(time, duration, null)) {
                return track;
            }
            i++;
        }
        track = new Track(timeline, i);
        tracks.put(i, track);
        return track;
    }
}

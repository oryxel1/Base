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

                }
            }}
        }

        return result;
    }
//
//    // TODO: Sub elements....
//    public static Map<Integer, Track> parse(Timeline timeline, Scenario scenario) {
//        final Map<Integer, List<Pair<Long, Long>>> occupies = new HashMap<>();
//        final Map<Integer, Pair<Pair<Object, RenderLayer>, Long>> elementMap = new HashMap<>();
//        final Map<Integer, Pair<PlaySoundEvent, Long>> soundMap = new HashMap<>();
//
//        final Map<Integer, Track> trackMap = new HashMap<>();
//        long elTime = 0;
//        for (Timestamp timestamp : scenario.getTimestamps()) {
//            elTime += timestamp.time();
//            for (Event e : timestamp.events()) {
//                switch (e) {
//                    case AddElementEvent event -> {
//                        if (trackMap.get(event.id()) == null) {
//                            trackMap.put(event.id(), new Track(timeline, event.id()));
//                        }
//
//                        final Pair<Pair<Object, RenderLayer>, Long> current = elementMap.get(event.id());
//                        if (current != null) {
//                            long maxTime = TimeCompiler.compileTime(current.left().left());
//                            long duration = maxTime == Long.MAX_VALUE ? elTime - current.right() : Math.min(maxTime, elTime - current.right());
//                            trackMap.get(event.id()).put(current.right(), duration, current.left().left(), current.left().left(), timestamp.waitForDialogue());
//                            occupy(occupies, event.id(), current.right(), current.right() + duration);
//                        }
//
//                        elementMap.put(event.id(), new Pair<>(new Pair<>(event.element(), event.layer()), elTime));
//                    }
//                    case RemoveElementEvent event -> {
//                        final Pair<Pair<Object, RenderLayer>, Long> cache = elementMap.remove(event.id());
//                        if (cache != null) {
//                            if (trackMap.get(event.id()) == null) {
//                                trackMap.put(event.id(), new Track(timeline, event.id()));
//                            }
//
//                            trackMap.get(event.id()).put(cache.right(), new Pair<>(new Track.ObjectOrEvent(cache.left().left(), cache.left().right(), null, timestamp.waitForDialogue()), elTime - cache.right()));
//                            occupy(occupies, event.id(), cache.right(), elTime);
//                        }
//                    }
//                    case AttachElementEvent event -> {
//                    }
//                    case PlaySoundEvent event -> {
//                        final Sound sound = event.sound();
//                        final Pair<PlaySoundEvent, Long> current = soundMap.get(sound.id());
//                        if (current != null) {
//                            long duration = AudioUtils.toDuration(scenario.getName(), sound.file());
//                            int id = findNonOccupiedSlot(elTime, duration, occupies);
//                            if (trackMap.get(id) == null) {
//                                trackMap.put(id, new Track(timeline, id));
//                            }
//
//                            final SoundAsElement element = new SoundAsElement(current.left().sound(), (int) current.left().duration(), Integer.MIN_VALUE, current.left().start(), duration);
//                            trackMap.get(id).put(current.right(), new Pair<>(new Track.ObjectOrEvent(element, null, null, timestamp.waitForDialogue()), duration));
//                            occupy(occupies, id, current.right(), current.right() + duration);
//                        }
//
//                        soundMap.put(sound.id(), new Pair<>(event, elTime));
//                    }
//                    case StopSoundEvent event -> {
//                        final Pair<PlaySoundEvent, Long> cache = soundMap.remove(event.id());
//                        if (cache != null) {
//                            if (trackMap.get(event.id()) == null) {
//                                trackMap.put(event.id(), new Track(timeline, event.id()));
//                            }
//
//                            final SoundAsElement element = new SoundAsElement(cache.left().sound(), (int) cache.left().duration(), event.duration(), cache.left().start(), elTime - cache.right());
//                            trackMap.get(event.id()).put(cache.right(), new Pair<>(new Track.ObjectOrEvent(element, null, null, timestamp.waitForDialogue()), elTime - cache.right()));
//                            occupy(occupies, event.id(), cache.right(), elTime);
//                        }
//                    }
//                    default -> {
//                        long duration = TimeCompiler.compileTime(e);
//                        int id = findNonOccupiedSlot(elTime, duration, occupies);
//
//                        if (trackMap.get(id) == null) {
//                            trackMap.put(id, new Track(timeline, id));
//                        }
//                        trackMap.get(id).put(elTime, new Pair<>(new Track.ObjectOrEvent(e, null, null, timestamp.waitForDialogue()), duration));
//                        occupy(occupies, id, elTime, elTime + duration);
////                        elTime += duration;
//                    }
//                }
//
//                // Handle elements that auto delete (self-destruct) itself....
//                for (Map.Entry<Integer, Pair<Pair<Object, RenderLayer>, Long>> entry : elementMap.entrySet()) {
//                    Pair<Pair<Object, RenderLayer>, Long> p = entry.getValue();
//                    occupy(occupies, entry.getKey(), p.right(), TimeCompiler.compileTime(p.left().left()));
//                    if (trackMap.get(entry.getKey()) == null) {
//                        trackMap.put(entry.getKey(), new Track(timeline, entry.getKey()));
//                    }
//
//                    trackMap.get(entry.getKey()).put(p.right(), new Pair<>(new Track.ObjectOrEvent(p.left().left(), p.left().right(), null, timestamp.waitForDialogue()), TimeCompiler.compileTime(p.left().left())));
//                }
//                // Handle sound/audio that play tills the end!
//                for (Map.Entry<Integer, Pair<PlaySoundEvent, Long>> entry : soundMap.entrySet()) {
//                    Pair<PlaySoundEvent, Long> p = entry.getValue();
//                    final Sound sound = p.left().sound();
//                    long duration = AudioUtils.toDuration(scenario.getName(), sound.file());
//
//                    occupy(occupies, entry.getKey(), p.right(), duration);
//                    if (trackMap.get(entry.getKey()) == null) {
//                        trackMap.put(entry.getKey(), new Track(timeline, entry.getKey()));
//                    }
//
//                    final SoundAsElement element = new SoundAsElement(p.left().sound(), (int) p.left().duration(), 0, p.left().start(), duration);
//                    trackMap.get(entry.getKey()).put(p.right(), new Pair<>(new Track.ObjectOrEvent(element, null, null, timestamp.waitForDialogue()), duration));
//                }
//            }
//        }
//
//        return Collections.synchronizedMap(trackMap);
//    }

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

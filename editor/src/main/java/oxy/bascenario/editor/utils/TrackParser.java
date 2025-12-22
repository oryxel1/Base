package oxy.bascenario.editor.utils;

import oxy.bascenario.api.Scenario;
import oxy.bascenario.api.Timestamp;
import oxy.bascenario.api.event.api.Event;
import oxy.bascenario.api.event.dialogue.AddDialogueEvent;
import oxy.bascenario.api.event.dialogue.ShowOptionsEvent;
import oxy.bascenario.api.event.dialogue.StartDialogueEvent;
import oxy.bascenario.api.event.element.AddElementEvent;
import oxy.bascenario.api.event.element.AttachElementEvent;
import oxy.bascenario.api.event.element.RemoveElementEvent;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.editor.TimeCompiler;
import oxy.bascenario.editor.element.Timeline;
import oxy.bascenario.editor.element.Track;
import oxy.bascenario.utils.Pair;

import java.util.*;

public class TrackParser {
    // TODO: Sub elements....
    public static List<Timestamp> parse(Map<Integer, Track> tracks) {
        final List<Timestamp> timestamps = new ArrayList<>();

        final Map<Long, Pair<Boolean, List<Event<?>>>> events = new TreeMap<>();
        for (Map.Entry<Integer, Track> entry : tracks.entrySet()) {
            Integer i = entry.getKey(); Track track = entry.getValue();
            track.getElements().forEach((l, pair) -> {
                final Pair<Boolean, List<Event<?>>> list = events.computeIfAbsent(l, n -> new Pair<>(pair.left().requireWait(), new ArrayList<>()));
                if (pair.left().object() instanceof Event<?> event) {
                    list.right().add(event);
                } else {
                    list.right().add(new AddElementEvent(i, pair.left().object(), pair.left().layer()));
                    events.computeIfAbsent(l + pair.right(), n -> new Pair<>(pair.left().requireWait(), new ArrayList<>())).right().add(new RemoveElementEvent(i));
                }
            });
        }

        long last = 0;
        for (Map.Entry<Long, Pair<Boolean, List<Event<?>>>> entry : events.entrySet()) {
            final Long time = entry.getKey();
            long delay = time - last;
            last = time;

            timestamps.add(new Timestamp(entry.getValue().left(), delay, entry.getValue().right()));
        }

        return timestamps;
    }

    // TODO: Sub elements....
    public static Map<Integer, Track> parse(Timeline timeline, Scenario scenario) {
        final Map<Integer, List<Pair<Long, Long>>> occupies = new HashMap<>();
        final Map<Integer, Pair<Pair<Object, RenderLayer>, Long>> elementMap = new HashMap<>(), subElementMap = new HashMap<>();

        final Map<Integer, Track> trackMap = new HashMap<>();
        long elTime = 0;
        for (Timestamp timestamp : scenario.getTimestamps()) {
            elTime += timestamp.time();
            for (Event<?> e : timestamp.events()) {
                if (e instanceof AddElementEvent event) {
                    if (trackMap.get(event.getId()) == null) {
                        trackMap.put(event.getId(), new Track(timeline, event.getId()));
                    }

                    final Pair<Pair<Object, RenderLayer>, Long> current = elementMap.get(event.getId());
                    if (current != null) {
                        long maxTime = TimeCompiler.timeFromElement(current.left().left());
                        long duration = maxTime == Long.MAX_VALUE ? elTime - current.right() : Math.min(maxTime, elTime - current.right());
                        trackMap.get(event.getId()).put(current.right(), new Pair<>(new Track.Cache(current.left().left(), current.left().right(), null, timestamp.waitForDialogue()), duration));
                        occupy(occupies, event.getId(), current.right(), current.right() + duration);
                    }

                    elementMap.put(event.getId(), new Pair<>(new Pair<>(event.getElement(), event.getLayer()), elTime));
                } else if (e instanceof RemoveElementEvent event) {
                    final Pair<Pair<Object, RenderLayer>, Long> cache = elementMap.remove(event.getId());
                    if (cache != null) {
                        if (trackMap.get(event.getId()) == null) {
                            trackMap.put(event.getId(), new Track(timeline, event.getId()));
                        }

                        trackMap.get(event.getId()).put(cache.right(), new Pair<>(new Track.Cache(cache.left().left(), cache.left().right(), null, timestamp.waitForDialogue()), elTime - cache.right()));
                        occupy(occupies, event.getId(), cache.right(), elTime);
                    }
                } else if (e instanceof AttachElementEvent event) {
//                    subElementMap.put(event.getSubId(), new Pair<>(new Pair<>(event.getElement(), null), elTime));
                } else if (e instanceof AddDialogueEvent event) {
                    long duration = TimeCompiler.compileTime(event.getDialogues());
                    int id = findNonOccupiedSlot(elTime, duration, occupies);

                    if (trackMap.get(id) == null) {
                        trackMap.put(id, new Track(timeline, id));
                    }
                    trackMap.get(id).put(elTime, new Pair<>(new Track.Cache(event, null, null, timestamp.waitForDialogue()), duration));
                    occupy(occupies, id, elTime, elTime + duration);

                    elTime += duration;
                } else if (e instanceof StartDialogueEvent event) {
                    long duration = TimeCompiler.compileTime(event.getDialogues());
                    int id = findNonOccupiedSlot(elTime, duration, occupies);

                    if (trackMap.get(id) == null) {
                        trackMap.put(id, new Track(timeline, id));
                    }
                    trackMap.get(id).put(elTime, new Pair<>(new Track.Cache(event, null, null, timestamp.waitForDialogue()), duration));
                    occupy(occupies, id, elTime, elTime + duration);

                    elTime += duration;
                }
                // Handle elements that auto delete (self-destruct) itself....
                for (Map.Entry<Integer, Pair<Pair<Object, RenderLayer>, Long>> entry : elementMap.entrySet()) {
                    Pair<Pair<Object, RenderLayer>, Long> p = entry.getValue();
                    occupy(occupies, entry.getKey(), p.right(), TimeCompiler.timeFromElement(p.left().left()));
                    if (trackMap.get(entry.getKey()) == null) {
                        trackMap.put(entry.getKey(), new Track(timeline, entry.getKey()));
                    }

                    trackMap.get(entry.getKey()).put(p.right(), new Pair<>(new Track.Cache(p.left().left(), p.left().right(), null, timestamp.waitForDialogue()), TimeCompiler.timeFromElement(p.left().left())));
                }
            }
        }

        return Collections.synchronizedMap(trackMap);
    }

    private static void occupy(Map<Integer, List<Pair<Long, Long>>> occupies, int index, long start, long end) {
        List<Pair<Long, Long>> list = occupies.computeIfAbsent(index, k -> new ArrayList<>());
        list.add(new Pair<>(start, end));
    }

    private static int findNonOccupiedSlot(long time, long duration, Map<Integer, List<Pair<Long, Long>>> occupies) {
        int i = 0;
        List<Pair<Long, Long>> pairs;
        while ((pairs = occupies.get(i)) != null) {
            boolean intersect = false;
            for (Pair<Long, Long> pair : pairs) {
                final long maxTime = pair.right(), minTime = pair.left();
                if (maxTime >= time && minTime <= time + duration) {
                    intersect = true;
                    i++;
                    break;
                }
            }

            if (!intersect) {
                break;
            }
        }

        return i;
    }
}

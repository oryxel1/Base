package oxy.base.editor.utils;

import oxy.base.api.Scenario;
import oxy.base.api.Timestamp;
import oxy.base.api.effects.Sound;
import oxy.base.api.event.api.Event;
import oxy.base.api.event.dialogue.AddDialogueEvent;
import oxy.base.api.event.dialogue.ShowOptionsEvent;
import oxy.base.api.event.dialogue.ShowQuestionSelectionEvent;
import oxy.base.api.event.dialogue.StartDialogueEvent;
import oxy.base.api.event.element.AddElementEvent;
import oxy.base.api.event.element.RemoveElementEvent;
import oxy.base.api.event.sound.PlaySoundEvent;
import oxy.base.api.event.sound.StopSoundEvent;
import oxy.base.editor.timeline.ObjectOrEvent;
import oxy.base.editor.timeline.Timeline;
import oxy.base.utils.math.Pair;

import java.util.*;

public class TrackParser {
    // TODO: Sub elements....
    public static List<Timestamp> parse(List<ObjectOrEvent> objects) {
        final List<Timestamp> timestamps = new ArrayList<>();

        final Map<Long, Pair<Boolean, List<Event>>> events = new TreeMap<>();

        objects.forEach((object) -> {
            final Pair<Boolean, List<Event>> list = events.computeIfAbsent(object.start, n -> new Pair<>(object.requireWait, new ArrayList<>()));
            if (object.object instanceof Event event) {
                list.right().add(event);
            } else if (object.object instanceof SoundAsElement(Sound sound, int in, int out, float start, long max)) {
                list.right().add(new PlaySoundEvent(sound, in, start));
                if (out != Integer.MIN_VALUE && sound != null) {
                    events.computeIfAbsent(object.start + object.duration, n -> new Pair<>(object.requireWait, new ArrayList<>())).right().add(new StopSoundEvent(sound.id(), out));
                }
            } else {
                list.right().add(new AddElementEvent(object.track, object.vec2, object.object, object.layer));
                events.computeIfAbsent(object.start + object.duration, n -> new Pair<>(object.requireWait, new ArrayList<>())).right().add(new RemoveElementEvent(object.track));
            }
        });

        long last = 0;
        for (Map.Entry<Long, Pair<Boolean, List<Event>>> entry : events.entrySet()) {
            final Long time = entry.getKey();
            long delay = time - last;
            last = time;

            long max = 0;
            for (Event event : entry.getValue().right()) {
                if (event instanceof StartDialogueEvent || event instanceof AddDialogueEvent || event instanceof ShowOptionsEvent || event instanceof ShowQuestionSelectionEvent) {
                    max = Math.max(max, TimeCompiler.compileTime(event));
                }
            }
            last += max;

            timestamps.add(new Timestamp(entry.getValue().left(), null, (int) delay, entry.getValue().right()));
        }

//        System.out.println(Arrays.toString(timestamps.toArray()));

        return timestamps;
    }

    public static List<ObjectOrEvent> parse(Timeline timeline, Scenario scenario) {
        final List<ObjectOrEvent> result = new ArrayList<>();
        final Map<Integer, Map<Long, Pair<Long, Long>>> occupies = new HashMap<>();

        final Map<Integer, Object> previous = new HashMap<>();

        long time = 0;
        for (Timestamp timestamp : scenario.getTimestamps()) {
            time += timestamp.time();

            for (Event other : timestamp.events()) { switch (other) {
                case AddElementEvent event -> {
                    long duration = TimeCompiler.compileTime(event.element());

                    final ObjectOrEvent objectOrEvent = new ObjectOrEvent(timeline, event.id(), time, duration, event.element(), event.layer(), timestamp.waitForDialogue(), event.position());
                    previous.put(event.id(), objectOrEvent);
                    result.add(objectOrEvent);

                    occupies.computeIfAbsent(event.id(), i -> new HashMap<>()).put(time, new Pair<>(time, time + duration));
                }
                case RemoveElementEvent event -> {
                    Object object = previous.get(event.id());
                    if (!(object instanceof ObjectOrEvent oOE)) {
                        continue;
                    }

                    oOE.duration = Math.min(time - oOE.start, oOE.duration);

                    Map<Long, Pair<Long, Long>> track = occupies.get(oOE.track);
                    if (track != null) {
                        final Pair<Long, Long> pair = track.get(oOE.start);
                        if (pair != null) {
                            pair.right(pair.left() + oOE.duration);

                        }
                    }
                }
                case PlaySoundEvent event -> {
                    long duration = AudioUtils.toDuration(scenario.getName(), event.sound().file());
                    int track = findNonOccupiedSlot(time, duration, occupies);

                    final SoundAsElement element = new SoundAsElement(event.sound(), (int) event.duration(), 0, event.start(), duration);
                    final ObjectOrEvent objectOrEvent = new ObjectOrEvent(timeline, track, time, duration, element, null, timestamp.waitForDialogue(), null);
                    previous.put(track, objectOrEvent);
                    result.add(objectOrEvent);

                    occupies.computeIfAbsent(track, i -> new HashMap<>()).put(time, new Pair<>(time, time + duration));
                }
                case StopSoundEvent event -> {
                    Object object = previous.get(event.id());
                    if (!(object instanceof ObjectOrEvent oOE) || !(oOE.object instanceof SoundAsElement sound)) {
                        continue;
                    }

                    final SoundAsElement.Builder builder = sound.toBuilder();
                    builder.out(event.duration());

                    oOE.object = builder.build();
                    oOE.duration = Math.min(time - oOE.start, oOE.duration);

                    Map<Long, Pair<Long, Long>> track = occupies.get(oOE.track);
                    if (track != null) {
                        final Pair<Long, Long> pair = track.get(oOE.start);
                        if (pair != null) {
                            pair.right(pair.left() + oOE.duration);
                        }
                    }
                }
                default -> {
                    long duration = TimeCompiler.compileTime(other);
                    int track = findNonOccupiedSlot(time, duration, occupies);

                    final ObjectOrEvent objectOrEvent = new ObjectOrEvent(timeline, track, time, duration, other, null, timestamp.waitForDialogue(), null);
                    previous.put(track, objectOrEvent);
                    result.add(objectOrEvent);

                    occupies.computeIfAbsent(track, i -> new HashMap<>()).put(time, new Pair<>(time, time + duration));

                    if (other instanceof StartDialogueEvent || other instanceof AddDialogueEvent || other instanceof ShowOptionsEvent) {
                        time += TimeCompiler.compileTime(other);
                    }
                }
            }}
        }

        return result;
    }

    private static int findNonOccupiedSlot(long time, long duration, Map<Integer, Map<Long, Pair<Long, Long>>> occupies) {
        int i = 0;

        Map<Long, Pair<Long, Long>> map;
        while ((map = occupies.get(i)) != null) {
            boolean occupied = false;
            for (Pair<Long, Long> pair : map.values()) {
                final long maxTime = Math.abs(pair.right()), minTime = pair.left();
                if (maxTime >= time && minTime <= time + duration) {
                    occupied = true;
                    break;
                }
            }

            if (!occupied) {
                return i;
            }
            i++;
        }

        return i;
    }
}

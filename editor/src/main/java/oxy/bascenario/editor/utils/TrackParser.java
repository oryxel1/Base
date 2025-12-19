package oxy.bascenario.editor.utils;

import oxy.bascenario.api.Scenario;
import oxy.bascenario.api.Timestamp;
import oxy.bascenario.api.event.api.Event;
import oxy.bascenario.api.event.dialogue.AddDialogueEvent;
import oxy.bascenario.api.event.dialogue.StartDialogueEvent;
import oxy.bascenario.api.event.element.AddElementEvent;
import oxy.bascenario.api.event.element.AttachElementEvent;
import oxy.bascenario.api.event.element.RemoveElementEvent;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.editor.TimeCompiler;
import oxy.bascenario.editor.screens.element.Timeline;
import oxy.bascenario.editor.screens.element.Track;
import oxy.bascenario.utils.Pair;

import java.util.HashMap;
import java.util.Map;

public class TrackParser {
    // TODO: Sub elements....
    public static Map<Integer, Track> parse(Timeline timeline, Scenario scenario) {
        final Map<Integer, Pair<Long, Long>> occupies = new HashMap<>();
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
                        trackMap.get(event.getId()).getElements().put(current.right(), new Pair<>(new Track.Cache(current.left().left(), current.left().right(), null), duration));
                        occupies.put(event.getId(), new Pair<>(current.right(), current.right() + duration));
                    }

                    elementMap.put(event.getId(), new Pair<>(new Pair<>(event.getElement(), event.getLayer()), elTime));
                } else if (e instanceof RemoveElementEvent event) {
                    final Pair<Pair<Object, RenderLayer>, Long> cache = elementMap.remove(event.getId());
                    if (cache != null) {
                        if (trackMap.get(event.getId()) == null) {
                            trackMap.put(event.getId(), new Track(timeline, event.getId()));
                        }

                        trackMap.get(event.getId()).getElements().put(cache.right(), new Pair<>(new Track.Cache(cache.left().left(), cache.left().right(), null), elTime - cache.right()));
                        occupies.put(event.getId(), new Pair<>(cache.right(), elTime));
                    }
                } else if (e instanceof AttachElementEvent event) {
//                    if (trackMap.get(event.getId()) == null) {
//                        elementMap.forEach((k, p) -> trackMap.get(k).getElements().put(p.right(), new Pair<>(new Track.Cache(p.left().left(), p.left().right(), null), TimeCompiler.timeFromElement(p.left().left()))));
//                        continue;
//                    }
//
//                    subElementMap.put(event.getSubId(), new Pair<>(new Pair<>(event.getElement(), null), elTime));
                } else if (e instanceof AddDialogueEvent event) {
                    long duration = TimeCompiler.compileTime(event.getDialogues());
                    elTime += duration;
                } else if (e instanceof StartDialogueEvent event) {
                    long duration = TimeCompiler.compileTime(event.getDialogues());
                    elTime += duration;
                }
            }
        }

        // Handle elements that auto delete (self-destruct) itself....
        elementMap.forEach((k, p) -> trackMap.get(k).getElements().put(p.right(), new Pair<>(new Track.Cache(p.left().left(), p.left().right(), null), TimeCompiler.timeFromElement(p.left().left()))));

        return trackMap;
    }
}

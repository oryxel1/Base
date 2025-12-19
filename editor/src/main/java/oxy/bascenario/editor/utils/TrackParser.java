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
    public static Map<Integer, Track> parse(Timeline timeline, Scenario scenario) {
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
                        trackMap.get(event.getId()).getElements().put(current.right(), new Pair<>(new Track.ElementCache(current.left().left(), current.left().right()), elTime - current.right()));
                    }

                    elementMap.put(event.getId(), new Pair<>(new Pair<>(event.getElement(), event.getLayer()), elTime));
                } else if (e instanceof RemoveElementEvent event) {
                    final Pair<Pair<Object, RenderLayer>, Long> cache = elementMap.remove(event.getId());
                    if (cache != null) {
                        if (trackMap.get(event.getId()) == null) {
                            trackMap.put(event.getId(), new Track(timeline, event.getId()));
                        }

                        trackMap.get(event.getId()).getElements().put(cache.right(), new Pair<>(new Track.ElementCache(cache.left().left(), cache.left().right()), elTime - cache.right()));
                    }
                } else if (e instanceof AttachElementEvent event) {
                    if (trackMap.get(event.getId()) == null) {
                        elementMap.forEach((k, p) -> trackMap.get(k).getElements().put(p.right(), new Pair<>(new Track.ElementCache(p.left().left(), p.left().right()), TimeCompiler.timeFromElement(p.left().left()))));
                        continue;
                    }

//                    subElementMap.put(event.getSubId(), )
                } else if (e instanceof AddDialogueEvent event) {

                } else if (e instanceof StartDialogueEvent event) {

                }
            }

            // Handle elements that auto delete (self-destruct) itself....
            elementMap.forEach((k, p) -> trackMap.get(k).getElements().put(p.right(), new Pair<>(new Track.ElementCache(p.left().left(), p.left().right()), TimeCompiler.timeFromElement(p.left().left()))));
        }

        return trackMap;
    }
}

package oxy.bascenario.editor.element;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import oxy.bascenario.api.render.RenderLayer;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

@RequiredArgsConstructor
@ToString
public class Track {
    public final Timeline timeline;
    @Getter
    private final int index;

    // key: start time, pair: a=element, b=duration.
    @Getter
    private final Map<Long, ObjectOrEvent> objects = new TreeMap<>();

    public long prevObjectStart; // meant for when parsing....

    public void remove(long l) {
        ObjectOrEvent object = this.objects.remove(l);

        if (object.renderer == timeline.getSelectedElement()) {
            timeline.setSelectedElement(null);
        }
    }

    public void put(long start, ObjectOrEvent object) {
        this.objects.put(start, object);
        timeline.updateScenario(true);
    }

    public void put(long start, long duration, Object object, RenderLayer layer, boolean wait) {
        final ObjectOrEvent objectOrEvent = new ObjectOrEvent(start, duration, null, object, layer, wait);
        objectOrEvent.renderer = new ObjectRenderer(this, timeline, objectOrEvent);
        put(start, objectOrEvent);
    }

    public void render(float x, float y, float width) {
        final Iterator<ObjectOrEvent> iterator = objects.values().iterator();

        ObjectOrEvent current, next = null;
        boolean first = true;
        while (first || next != null) {
            first = false;
            current = next == null ? iterator.next() : next;
            if (iterator.hasNext()) {
                next = iterator.next();
            } else {
                next = null;
            }

            current.renderer.render(x, y, width, next);
        }
    }

    @AllArgsConstructor
    public static final class ObjectOrEvent {
        public long start, duration;
        public ObjectRenderer renderer;

        public Object object;
        public RenderLayer layer;
        public boolean requireWait;
    }

    public boolean isNotOccupied(long time, long duration, ObjectOrEvent current) {
        for (ObjectOrEvent object : objects.values()) {
            final long maxTime = object.start + object.duration, minTime = object.start;
            if (maxTime >= time && minTime <= time + duration && object != current) {
                return false;
            }
        }

        return true;
    }
}

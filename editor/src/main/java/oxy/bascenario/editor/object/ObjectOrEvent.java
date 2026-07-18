package oxy.bascenario.editor.object;

import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.editor.object.values.ObjectTransform;

import java.util.EnumMap;

public class ObjectOrEvent {
    public long start, duration;

    public Object object;
    public RenderLayer layer;
    public boolean requireWait;

    public ObjectOrEvent(long start, long duration, Object object, RenderLayer render, boolean requireWait) {
        this.start = start;
        this.duration = duration;
        this.object = object;
        this.layer = render;
        this.requireWait = requireWait;
    }

    private final EnumMap<ObjectTransform, Object> transformations = new EnumMap<>(ObjectTransform.class);

}

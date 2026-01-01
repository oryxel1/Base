package oxy.bascenario.editor.timeline;

import oxy.bascenario.api.render.RenderLayer;

public class ObjectOrEvent {
    public int track;
    public long start, duration;
    public ObjectRenderer renderer;

    public Object object;
    public RenderLayer layer;
    public final boolean requireWait;

    public ObjectOrEvent(Timeline timeline, int track, long start, long duration, Object object, RenderLayer layer, boolean requireWait) {
        this.track = track;
        this.start = start;
        this.duration = duration;
        this.renderer = new ObjectRenderer(timeline, this);
        this.object = object;
        this.layer = layer;
        this.requireWait = requireWait;
    }
}

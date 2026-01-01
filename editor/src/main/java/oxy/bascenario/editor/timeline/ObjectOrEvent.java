package oxy.bascenario.editor.timeline;

import lombok.AllArgsConstructor;
import oxy.bascenario.api.render.RenderLayer;

@AllArgsConstructor
public class ObjectOrEvent {
    public int track;
    public long start, duration;
    public ObjectRenderer renderer;

    public Object object;
    public RenderLayer layer;
    public boolean requireWait;
}

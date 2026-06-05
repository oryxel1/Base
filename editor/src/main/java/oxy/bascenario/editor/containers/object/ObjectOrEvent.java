package oxy.bascenario.editor.containers.object;

import lombok.AllArgsConstructor;
import oxy.bascenario.api.render.RenderLayer;

@AllArgsConstructor
public class ObjectOrEvent {
    public long start, duration;

    public Object object;
    public RenderLayer layer;
    public boolean requireWait;
}

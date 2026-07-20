package oxy.bascenario.editor.object;

import lombok.Getter;
import lombok.experimental.Accessors;
import net.lenni0451.commons.color.Color;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.editor.object.values.ObjectTransform;

import java.util.EnumMap;

@Accessors(fluent = true)
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

    @Getter
    private final EnumMap<ObjectTransform, Object> transformations = new EnumMap<>(ObjectTransform.class);

    public Color color = Color.WHITE;
    public Color overlapColor = Color.TRANSPARENT;
}

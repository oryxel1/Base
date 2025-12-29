package oxy.bascenario.api.event.color;

import lombok.Builder;
import net.lenni0451.commons.color.Color;
import oxy.bascenario.api.event.api.Event;
import oxy.bascenario.api.render.RenderLayer;

import java.util.Optional;

// If the id is present then this will try to find an element with that id, else the overlay will be on top of everything.
@Builder(toBuilder = true, builderClassName = "Builder")
public record ColorOverlayEvent(Optional<Integer> id, int duration, Color color,
                                RenderLayer renderLayer) implements Event {
    public ColorOverlayEvent(RenderLayer layer, int duration, Color color) {
        this(Optional.empty(), duration, color, layer == null ? RenderLayer.TOP : layer);
    }

    public ColorOverlayEvent(int duration, Color color) {
        this(Optional.empty(), duration, color, RenderLayer.TOP);
    }

    public ColorOverlayEvent(Integer id, int duration, Color color) {
        this(id == null ? Optional.empty() : Optional.of(id), duration, color, null);
    }
}

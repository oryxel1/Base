package oxy.bascenario.api.event;

import lombok.Getter;
import net.lenni0451.commons.color.Color;
import oxy.bascenario.api.event.api.Event;
import oxy.bascenario.api.render.RenderLayer;

import java.util.Optional;

// If the id is present then this will try to find an element with that id, else the overlay will be on top of everything.
@SuppressWarnings("ALL")
@Getter
public class ColorOverlayEvent extends Event<ColorOverlayEvent> {
    private final Optional<Integer> id;
    private final int duration;
    private final Color color;
    private final RenderLayer renderLayer;

    public ColorOverlayEvent(RenderLayer layer, int duration, Color color) {
        this.duration = duration;
        this.color = color;
        this.renderLayer = layer;
        this.id = Optional.empty();
    }

    public ColorOverlayEvent(int duration, Color color) {
        this.duration = duration;
        this.color = color;
        this.renderLayer = RenderLayer.TOP;
        this.id = Optional.empty();
    }

    public ColorOverlayEvent(Integer id, int duration, Color color) {
        this.renderLayer = null;
        this.duration = duration;
        this.color = color;
        this.id = id == null ? Optional.empty() : Optional.of(id);
    }

    @Override
    public String type() {
        return "color-overlay";
    }

    @Override
    public ColorOverlayEvent empty() {
        return new ColorOverlayEvent(RenderLayer.TOP, -1, Color.BLACK);
    }
}

package oxy.bascenario.api.event;

import lombok.Getter;
import net.lenni0451.commons.color.Color;
import oxy.bascenario.api.effects.Fade;
import oxy.bascenario.api.event.api.RenderEvent;
import oxy.bascenario.api.render.RenderLayer;

import java.util.Optional;

// If the id is present then this will try to find an element with that id, else the overlay will be on top of everything.
@SuppressWarnings("ALL")
@Getter
public class ColorOverlayEvent extends RenderEvent<ColorOverlayEvent> {
    private final Optional<Integer> id;
    private final Fade fade;
    private final Color color;

    public ColorOverlayEvent(Fade fade, Color color) {
        super(RenderLayer.TOP, 0);
        this.fade = fade;
        this.color = color;
        this.id = Optional.empty();
    }

    public ColorOverlayEvent(RenderLayer layer, Fade fade, Color color) {
        super(layer, 0);
        this.fade = fade;
        this.color = color;
        this.id = Optional.empty();
    }

    public ColorOverlayEvent(Integer id, Fade fade, Color color) {
        super(RenderLayer.TOP, 0); // render layer is not relevant here...
        this.fade = fade;
        this.color = color;
        this.id = id == null ? Optional.empty() : Optional.of(id);
    }

    @Override
    public String type() {
        return "color-overlay";
    }

    @Override
    public ColorOverlayEvent empty() {
        return new ColorOverlayEvent(RenderLayer.TOP, Fade.DISABLED, Color.BLACK);
    }
}

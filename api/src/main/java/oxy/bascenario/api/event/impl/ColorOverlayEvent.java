package oxy.bascenario.api.event.impl;

import lombok.Getter;
import oxy.bascenario.api.effects.Fade;
import oxy.bascenario.api.event.RenderEvent;
import oxy.bascenario.api.render.RenderLayer;

// I guess use java.awt so we don't have to expose the rendering color to the api.
import java.awt.Color;

@Getter
public class ColorOverlayEvent extends RenderEvent<ColorOverlayEvent> {
    private final Fade in, out;
    private final Color color;

    public ColorOverlayEvent(long duration, Fade in, Fade out, Color color) {
        super(RenderLayer.TOP, duration + in.duration() + out.duration() + 100L);
        this.in = in;
        this.out = out;
        this.color = color;
    }

    public ColorOverlayEvent(RenderLayer layer, long duration, Fade in, Fade out, Color color) {
        super(layer, duration + in.duration() + out.duration() + 100L);
        this.in = in;
        this.out = out;
        this.color = color;
    }

    @Override
    public String type() {
        return "color-overlay";
    }

    @Override
    public ColorOverlayEvent empty() {
        return new ColorOverlayEvent(RenderLayer.TOP, 1000, Fade.DISABLED, Fade.DISABLED, Color.BLACK);
    }
}

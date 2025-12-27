package oxy.bascenario.editor.inspector.impl.events;

import oxy.bascenario.api.event.color.ColorOverlayEvent;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.utils.ImGuiUtils;

import java.util.Optional;

public class ColorOverlayInspector {
    public static ColorOverlayEvent render(ColorOverlayEvent event) {
        ColorOverlayEvent.Builder builder = event.toBuilder();
        if (event.getId().isPresent()) {
            builder.id(Optional.of(Math.abs(ImGuiUtils.inputInt("Target Track", event.getId().get()))));
        }

        builder.duration(ImGuiUtils.sliderInt("Fade Duration", event.getDuration(), 0, 60000));

        builder.color(ImGuiUtils.color("Color", event.getColor()));

        if (event.getId().isEmpty()) {
            builder.renderLayer(RenderLayer.values()[ImGuiUtils.combo("Render Layer", event.getRenderLayer().ordinal(), RenderLayer.getAlls())]);
        }

        return builder.build();
    }
}

package oxy.bascenario.editor.inspector.impl.events;

import oxy.bascenario.api.event.color.ColorOverlayEvent;
import oxy.bascenario.api.event.color.SetColorEvent;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.utils.ImGuiUtils;

import java.util.Optional;

public class ColorInspector {
    public static SetColorEvent render(SetColorEvent event) {
        SetColorEvent.Builder builder = event.toBuilder();
        builder.id(Math.abs(ImGuiUtils.inputInt("Target Track", event.id())));
        builder.duration(ImGuiUtils.sliderInt("Fade Duration", event.duration(), 0, 60000));

        builder.color(ImGuiUtils.color("Color", event.color()));
        return builder.build();
    }

    public static ColorOverlayEvent render(ColorOverlayEvent event) {
        ColorOverlayEvent.Builder builder = event.toBuilder();
        if (event.id().isPresent()) {
            builder.id(Optional.of(Math.abs(ImGuiUtils.inputInt("Target Track", event.id().get()))));
        }

        builder.duration(ImGuiUtils.sliderInt("Fade Duration", event.duration(), 0, 60000));

        builder.color(ImGuiUtils.color("Color", event.color()));

        if (event.id().isEmpty()) {
            builder.renderLayer(RenderLayer.values()[ImGuiUtils.combo("Render Layer", event.renderLayer().ordinal(), RenderLayer.getAlls())]);
        }

        return builder.build();
    }
}

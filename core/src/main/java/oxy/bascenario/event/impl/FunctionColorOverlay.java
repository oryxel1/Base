package oxy.bascenario.event.impl;

import com.google.gson.JsonObject;
import net.lenni0451.commons.color.Color;
import oxy.bascenario.api.effects.Fade;
import oxy.bascenario.api.event.ColorOverlayEvent;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.event.base.FunctionEvent;
import oxy.bascenario.screens.ScenarioScreen;
import oxy.bascenario.screens.renderer.element.base.ElementRenderer;
import oxy.bascenario.serializers.utils.GsonUtils;

public class FunctionColorOverlay extends FunctionEvent<ColorOverlayEvent> {
    public FunctionColorOverlay(ColorOverlayEvent event) {
        super(event);
    }

    @Override
    public void start(ScenarioScreen screen) {
        ElementRenderer<?> renderer = screen.getElements().get(event.getId().isEmpty() ? Integer.MIN_VALUE + event.layer().ordinal() : event.getId().get());
        if (renderer != null) {
            renderer.getColor().set(event.getColor(), Math.max(0, event.getFade().duration()));
        }
    }

    @Override
    public void serialize(JsonObject serialized) {
        serialized.addProperty("id-present", event.getId().isPresent());
        if (event.getId().isPresent()) {
            serialized.addProperty("id", event.getId().get());
            serialized.addProperty("render-layer", event.layer().name());
        }

        final Color color = Color.fromRGBA(event.getColor().getRed(), event.getColor().getRed(), event.getColor().getRed(), event.getColor().getRed());
        serialized.addProperty("color", color.toRGBA());
        serialized.add("fade", GsonUtils.toJson(event.getFade()));
    }

    @Override
    public ColorOverlayEvent deserialize(JsonObject serialized) {
        final Fade fade = GsonUtils.getGson().fromJson(serialized.get("fade"), Fade.class);
        boolean present = serialized.get("id-present").getAsBoolean();


        Color color = Color.fromABGR(serialized.get("color").getAsInt());
        if (present) {
            return new ColorOverlayEvent(serialized.get("id").getAsInt(), fade, color);
        } else {
            final RenderLayer layer = RenderLayer.valueOf(serialized.get("render-layer").getAsString());
            return new ColorOverlayEvent(layer, fade, color);
        }
    }
}

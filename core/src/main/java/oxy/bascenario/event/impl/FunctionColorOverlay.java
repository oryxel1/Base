package oxy.bascenario.event.impl;

import com.google.gson.JsonObject;
import net.lenni0451.commons.animation.DynamicAnimation;
import oxy.bascenario.api.effects.Fade;
import oxy.bascenario.api.event.impl.ColorOverlayEvent;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.event.base.EventFunction;
import oxy.bascenario.screens.ScenarioScreen;
import oxy.bascenario.screens.renderer.base.ElementRenderer;
import oxy.bascenario.serializers.utils.GsonUtils;
import oxy.bascenario.utils.AnimationUtils;

public class FunctionColorOverlay extends EventFunction<ColorOverlayEvent> {
    private DynamicAnimation fade = AnimationUtils.dummy(1);
    public FunctionColorOverlay(ColorOverlayEvent event) {
        super(event);
    }

    private ElementRenderer<?> renderer;
    @Override
    public void start(ScenarioScreen screen) {
        if (event.getId().isPresent()) {
            ElementRenderer<?> renderer = screen.getElements().get(event.getId().get());
            if (renderer != null) {
                renderer.overlayColor(event.getColor(), Math.max(0, event.getFade().duration()));
            }
        } else {
            // TODO....
        }
    }

    @Override
    public void serialize(JsonObject serialized) {
        serialized.addProperty("id-present", event.getId().isPresent());
        if (event.getId().isPresent()) {
            serialized.addProperty("id", event.getId().get());
            serialized.addProperty("render-layer", event.layer().name());
        }

        serialized.addProperty("color", event.getColor().getRGB());
        serialized.add("fade", GsonUtils.toJson(event.getFade()));
    }

    @Override
    public ColorOverlayEvent deserialize(JsonObject serialized) {
        final Fade fade = GsonUtils.getGson().fromJson(serialized.get("fade"), Fade.class);
        boolean present = serialized.get("id-present").getAsBoolean();

        if (present) {
            return new ColorOverlayEvent(serialized.get("id").getAsInt(), fade, new java.awt.Color(serialized.get("color").getAsInt()));
        } else {
            final RenderLayer layer = RenderLayer.valueOf(serialized.get("render-layer").getAsString());
            return new ColorOverlayEvent(layer, fade, new java.awt.Color(serialized.get("color").getAsInt()));
        }
    }
}
